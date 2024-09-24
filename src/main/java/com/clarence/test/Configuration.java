package com.clarence.test;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class Configuration {
    private File file;
    private FileConfiguration fileConfiguration;
    private static int OBJECT_COUNT = 0;

    public Configuration(String name) {
        OBJECT_COUNT++;
        file = setupFile(name);
        fileConfiguration = loadConfiguration(file);
    }

    public void addDefault(String path, Object value) {
        fileConfiguration.addDefault(path, value);
        fileConfiguration.options().copyDefaults(true);
    }
    public ConfigurationSection createSection(String path) {
        return fileConfiguration.createSection(path);
    }

    public Set<String> getConfigkKeys(boolean isDeep) {
        return fileConfiguration.getKeys(isDeep);
    }
    public ConfigurationSection getSection(String path) {
       return fileConfiguration.getConfigurationSection(path);
    }

    private File setupFile(String name) {
        File dataFolder = Test.getPlugin(Test.class).getDataFolder();

        File file = new File(dataFolder, name);

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        if (!file.exists()) {
            try {
                file.createNewFile();
                Util.setConsoleMessage("Created a new file called as " + file.getName());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return file;
    }
    private FileConfiguration loadConfiguration(File file) {
        FileConfiguration fileConfiguration;
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        Util.setConsoleMessage("Loaded in " + file.getName());
        return  fileConfiguration;
    }
    public boolean hasUpdate(String name) {
        return fileConfiguration.getBoolean(name);
    }

    public void saveConfiguration() {
        try {
            fileConfiguration.save(file);
            Util.setConsoleMessage("Saved " + file.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void reloadConfiguration(){
        fileConfiguration = loadConfiguration(file);
    }
    public static int getObjectCount() {return OBJECT_COUNT;}
    public static void displayNumberOfConfigurationFiles() { Util.setConsoleMessage("Number of files " + Configuration.getObjectCount() + " in " + Test.getPlugin(Test.class).getDataFolder().getPath());}
}
