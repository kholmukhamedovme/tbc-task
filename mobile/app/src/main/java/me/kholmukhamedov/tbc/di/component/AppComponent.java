package me.kholmukhamedov.tbc.di.component;

import javax.inject.Singleton;

import dagger.Component;
import me.kholmukhamedov.tbc.di.module.AppModule;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    MainComponent.Builder mainComponentBuilder();

}
