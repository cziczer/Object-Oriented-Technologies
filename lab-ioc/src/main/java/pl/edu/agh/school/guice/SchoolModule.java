package pl.edu.agh.school.guice;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;
import pl.edu.agh.logger.ConsoleMessageSerializer;
import pl.edu.agh.logger.FileMessageSerializer;
import pl.edu.agh.logger.IMessageSerializer;
import pl.edu.agh.school.persistence.PersistenceManager;
import pl.edu.agh.school.persistence.SerializablePersistenceManager;

public class SchoolModule extends AbstractModule  {
    @Override
    protected void configure() {
        bind(PersistenceManager.class).to(SerializablePersistenceManager.class);
        bind(String.class).annotatedWith(Names.named("TEACHERS_FILE")).toInstance("teachers.dat");
        bind(String.class).annotatedWith(Names.named("CLASSES_FILE")).toInstance("classes.dat");
        Multibinder<IMessageSerializer> multibinder = Multibinder.newSetBinder(binder(), IMessageSerializer.class);
        multibinder.addBinding().to(FileMessageSerializer.class);
        multibinder.addBinding().to(ConsoleMessageSerializer.class);
        bind(String.class).annotatedWith(Names.named("LOG_FILENAME")).toInstance("persistence.log");
    }
}
