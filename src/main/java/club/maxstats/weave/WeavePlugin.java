package club.maxstats.weave;

import club.maxstats.weave.task.SetupTask;
import lombok.NonNull;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.TaskProvider;

/**
 * TODO: Add description here.
 *
 * @author Scherso (<a href="https://github.com/Scherso">...</a>), Max (<a href="https://github.com/exejar">...</a>)
 *         Nils <3 (<a href="https://github.com/Nilsen84">...</a>)
 * @version 1.0.0
 * @since 1.0.0
 */
public class WeavePlugin implements Plugin<Project> {

    /**
     * {@link Plugin#apply(Object)}
     *
     * @param target The target object
     */
    @Override
    public void apply(@NonNull Project target) {
        target.getPluginManager().apply(JavaPlugin.class);
        
        /* Initializing our SetupTask. */
        target.getTasks().create("setupDecompWorkspace", SetupTask.class);
    }

}
