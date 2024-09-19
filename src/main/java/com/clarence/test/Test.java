package com.clarence.test;

import com.technicjelle.UpdateChecker;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.CompletionException;

public final class Test extends JavaPlugin {
    private Configuration reportConfiguration, defaultConfiguration;
    private UpdateChecker updateChecker;
    @Override
    public void onEnable() {
        Test getPlugin = getPlugin(getClass());

        createConfigurations();
        Configuration.displayNumberOfConfigurationFiles();

        if (defaultConfiguration.hasUpdate("enabled_updates")) {
            updateChecker = createUpdateCheckerObject(getPlugin.getDescription().getAuthors().getFirst(), getPlugin.getDescription().getName(), getPlugin.getDescription().getVersion());
            try {
                updateChecker.check();
                updateChecker.logUpdateMessage(getLogger());
            }catch (CompletionException e) {
                e.getStackTrace();
                Util.setUpdateMessage("Could not find any update");
                updateChecker = null;
            }
        }

        getCommand("report").setExecutor(new reportCommand(this));
    }
    private Configuration createConfigurationObject(String name) { return new Configuration( name + ".yml"); }
    private UpdateChecker createUpdateCheckerObject(String author, String repoName, String currentVersion) { return new UpdateChecker(author, repoName, currentVersion);}
    private void createConfigurations() {
        defaultConfiguration = createConfigurationObject("config");
        defaultConfiguration.addDefault("enabled_updates", true);
        defaultConfiguration.saveConfiguration();

        reportConfiguration = createConfigurationObject("report");
        reportConfiguration.saveConfiguration();
    }
    public void setUpdateChecker(UpdateChecker updateChecker) { this.updateChecker = updateChecker; }
    public UpdateChecker getUpdateChecker() {return updateChecker; }
    public Configuration getReportConfiguration() {return reportConfiguration; }
    public Configuration getDefaultConfiguration() {return defaultConfiguration; }
}
