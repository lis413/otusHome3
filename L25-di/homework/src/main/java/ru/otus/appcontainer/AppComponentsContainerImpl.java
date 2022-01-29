package ru.otus.appcontainer;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Slf4j
public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        checkConfigClass(configClass);
            parseConfig(configClass);
    }


    private void parseConfig(Class<?> configClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Object config = configClass.getDeclaredConstructor().newInstance();
        Arrays.stream(configClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparingInt(method -> method.getAnnotation(AppComponent.class).order()))
                .forEach(method -> {
                     addMethod(method, config);
                });
    }

    private void addMethod(Method method, Object config){
        method.setAccessible(true);
        Class<?>[] parameters = method.getParameterTypes();
        Object[] args = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            args[i] = getAppComponent(parameters[i]);
        }
            newObject(method, config, args);
    }
    private void newObject(Method method, Object config, Object args){
        Object comp;
        try {
            comp = method.invoke(config, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        String name = method.getAnnotation(AppComponent.class).name();
        if (!appComponentsByName.containsKey(name)) {
            appComponentsByName.put(name, comp);
            appComponents.add(comp);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        return (C) appComponents.stream()
                .filter(c -> componentClass.isAssignableFrom(c.getClass()))
                .findFirst()
                .orElseThrow(() -> new NoBeanException(componentClass.getName()));
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        C component = (C) appComponentsByName.get(componentName);
        if (component == null) {
            throw new NoBeanException(componentName);
        }
        return component;
    }
}
