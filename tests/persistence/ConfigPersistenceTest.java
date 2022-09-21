package persistence;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ConfigPersistenceTest {

    private static final String folderPath = Paths.get("tests","persistence", "files").toAbsolutePath().toString();
    private static final String configPath = folderPath + "/config.json";
    private static final String expectedConfigPath = folderPath + "/expectedConfig.json";

    private ConfigPersistence _configPersistence;

    @After
    public void tearDown() {
        File f = new File(configPath);
        f.delete();
    }

    @Test
    public void onStart_WhenConfigFileDoesNotExist_ShouldCreateFile() {

        File file = new File(configPath);
        if (file.exists())
            Assert.fail("File already exits");
        else {
            _configPersistence = new ConfigPersistence(configPath);
            Assert.assertTrue(file.exists());
        }
    }

    @Test
    public void onSave_WhenConfigIsSet_ShouldCreateAConfigFileWithData() {

        String fileType = ConfigPersistence.IR;
        String frequency = "1";
        String inputFolderPath = "inputFolderPath";
        String outputFolderPath = "outputFolderPath";
        String errorFolderPath = "errorFolderPath";

        _configPersistence = new ConfigPersistence(configPath);

        _configPersistence.setFileType(fileType);
        _configPersistence.setFrequency(frequency);
        _configPersistence.setInputFolderPath(inputFolderPath);
        _configPersistence.setOutputFolderPath(outputFolderPath);
        _configPersistence.setErrorFolderPath(errorFolderPath);

        _configPersistence.save();
        _configPersistence.load();

        Assert.assertEquals(fileType, _configPersistence.getFileType());
        Assert.assertEquals(frequency, _configPersistence.getFrequency());
        Assert.assertEquals(inputFolderPath, _configPersistence.getInputFolderPath());
        Assert.assertEquals(outputFolderPath, _configPersistence.getOutputFolderPath());
        Assert.assertEquals(errorFolderPath, _configPersistence.getErrorFolderPath());
    }

    @Test
    public void onLoad_WhenMethodsIsCalled_ShouldLoadAllConfig() {
        _configPersistence = new ConfigPersistence(expectedConfigPath);

        Assert.assertEquals(_configPersistence.getFileType(), _configPersistence.IR);
        Assert.assertEquals(_configPersistence.getFrequency(), "777");
        Assert.assertEquals(_configPersistence.getInputFolderPath(), "/Users/luizescandiuzzi/Applications");
        Assert.assertEquals(_configPersistence.getOutputFolderPath(), "/Users/luizescandiuzzi/Desktop");
        Assert.assertEquals(_configPersistence.getErrorFolderPath(), "/Users/luizescandiuzzi/Applications");
    }
}