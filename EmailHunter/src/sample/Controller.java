package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import javafx.*;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {

    @FXML
    public TextField pro = new TextField();
    @FXML
    public TextField Country = new TextField();
    @FXML
    public ListView<String> list = new ListView<String>();
    @FXML
    public Button save = new Button();
    @FXML
            public Button button = new Button();
    @FXML
            public ProgressBar progress = new ProgressBar();
    ObservableList<String> listToAdd = FXCollections.observableArrayList();
    public  ArrayList<String> emailExtractText = new ArrayList<>();
    public  ArrayList<String> emails = new ArrayList<>();
    public  BufferedWriter writer = null;
    String[] socialMedia = {"linkedin.com", "facebook.com", "instagram.com", "youtube.com", "twitter.com", "reddit.com", "pinterest.com", "medium.com", "Quora.com"};
    String[] searchEngine = {"https://in.yahoo.com/everything/","https://www.bing.com/","https://www.google.com/"};
    String[] domainsName = {"gmail.com", "yahoo.com", "hotmail.com","aol.com", "outlook.com"};


    public void onClick(){
        save.setVisible(true);
        list.setVisible(true);
        pro.setVisible(false);
        button.setVisible(false);
        Country.setVisible(false);
        progress.setVisible(true);
        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                System.setProperty("Webdrive.chrome.driver", "G:\\3.Harsh\\harshChourasiyapersonal\\Java_Madules\\chromedriver_win32\\chromedriver.exe");
                ChromeOptions options = new ChromeOptions();
                options.setHeadless(true);
                String toGetCurrentUrl = null;
                WebDriver driver = new ChromeDriver(options);
                String pro1 = pro.getText();
                String country1 = Country.getText();
                for (int i =0; i< socialMedia.length; i++){
                    StringBuilder url = new StringBuilder();
                    url.append("site:"+ socialMedia[i] + " \"" + pro1+"\" " + "\"" + country1 + "\"");
                    for (int j =0; j< searchEngine.length; j++){
                        driver.get( searchEngine[j]);
                        try {
                            Thread.sleep(1000);
                        }catch
                        (Exception e){
                            e.getMessage();
                        }
                        if ( searchEngine[j].equals("https://www.google.com/")){
                            for (int x =0; x< domainsName.length; x++) {
                                driver.get( searchEngine[j]);
                                StringBuilder urlForGoogle = new StringBuilder();
                                urlForGoogle.append(url);
                                urlForGoogle.append(" \"" +  domainsName[x] + "\"");
                                try {
                                    WebElement input = driver.findElement(By.xpath("//input[@type='text']"));
                                    input.sendKeys(urlForGoogle + "\n");
                                    for (int y = 0; y < 10; y++) {
                                        try {
                                            java.util.List<WebElement> text = driver.findElements(By.xpath("//span[@class='st']"));
                                            for (int u = 0; u < text.size(); u++) {
                                                try {
                                                    try{
                                                        Thread.sleep(1000);
                                                    }catch (Exception e){
                                                        e.printStackTrace();
                                                    }
                                                    toGetCurrentUrl = driver.getCurrentUrl();
                                                    emailExtractText.add(text.get(u).getText());
                                                    toExtractEmailFromText();
                                                } catch (Exception e) {
                                                    System.out.println("Something wrong in adding item!");
                                                    e.printStackTrace();
                                                    continue;
                                                }
                                            }
                                            try {
                                                Thread.sleep(1000);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        } catch (Exception e) {
                                            try{
                                                Thread.sleep(1*60*1000);
                                            } catch (Exception b){
                                                b.printStackTrace();
                                            }
                                            driver.close();
                                            driver = new ChromeDriver();
                                            driver.navigate().to(toGetCurrentUrl);
                                            try{
                                                Thread.sleep(1*60*1000);
                                            } catch (Exception b){
                                                b.printStackTrace();
                                            }
                                        }
                                        try {
                                            driver.findElement(By.xpath("//*[@id=\"pnnext\"]/span[2]")).click();
                                        } catch (Exception e) {
                                            try{
                                                Thread.sleep(1*60*1000);
                                            } catch (Exception b){
                                                b.printStackTrace();
                                            }
                                            driver.close();
                                            driver = new ChromeDriver();
                                            driver.navigate().to(toGetCurrentUrl);
                                            try{
                                                Thread.sleep(1*60*1000);
                                            } catch (Exception b){
                                                b.printStackTrace();
                                            }
                                            System.out.println("Something wrong in google click");
                                            e.printStackTrace();
                                        }
                                    }
                                } catch (Exception e){
                                    System.out.println("Something wrong in whole google ");
                                    e.printStackTrace();

                                }
                            }
                        }
                        else if( searchEngine[j].equals("https://www.bing.com/")){
                            for (int x=0; x< domainsName.length; x++){
                                driver.get( searchEngine[j]);
                                StringBuilder urlForBing = new StringBuilder();
                                urlForBing.append(url);
                                urlForBing.append(" \"" +  domainsName[x] + "\"");
                                try {
                                    WebElement input = driver.findElement(By.xpath("//input[@type='search'and@name='q']"));
                                    input.sendKeys(urlForBing + "\n");
                                    Thread.sleep(500);

                                    for (int y = 0; y < 10; y++) {
                                        try {
                                            java.util.List<WebElement> text = driver.findElements(By.xpath("//p"));
                                            for (int u = 0; u < text.size(); u++) {
                                                try {
                                                    emailExtractText.add(text.get(u).getText());
                                                    toExtractEmailFromText();
                                                } catch (Exception e) {
                                                    System.out.println("Something wrong in adding item!");
                                                    e.printStackTrace();
                                                    continue;
                                                }
                                            }
                                            Thread.sleep(1000);
                                        }catch (Exception e){
                                            System.out.println("Something wrong in bing list!");
                                            e.printStackTrace();
                                        }
                                        try {
                                            WebElement nextButtons = driver.findElement(By.xpath("//a[@title='Next page']"));
                                            nextButtons.click();
                                        }catch (Exception e){
                                            driver.findElement(By.xpath("//*[@id=\"bnp_hfly_cta2\"]")).click();
                                            try {
                                                Thread.sleep(1000);
                                            }catch (Exception b){
                                                b.printStackTrace();
                                            }
                                            WebElement nextButtons = driver.findElement(By.xpath("//a[@title='Next page']"));
                                            nextButtons.click();
                                            System.out.println("Something went wrog in bing driver next click!");
                                            e.printStackTrace();
                                        }
                                    }
                                } catch (Exception e){
                                    System.out.println("Something wrong in whole bing");
                                    e.printStackTrace();
                                }
                            }
                        }
                        else if ( searchEngine[j].equals("https://in.yahoo.com/everything/")){
                            for (int x=0; x< domainsName.length; x++){
                                driver.get( searchEngine[j]);
                                StringBuilder urlForyahoo = new StringBuilder();
                                urlForyahoo.append(url);
                                urlForyahoo.append(" \"" +  domainsName[x] + "\"");
                                try {
                                    WebElement input = driver.findElement(By.xpath("//input[@type='text']"));
                                    input.sendKeys(urlForyahoo + "\n");
                                    try {
                                        Thread.sleep(500);
                                    } catch (Exception e) {
                                        e.getMessage();
                                    }
                                    for (int y = 0; y < 10; y++) {
                                        try {
                                            List<WebElement> text = driver.findElements(By.xpath("//p"));
                                            for (int u = 0; u < text.size(); u++) {
                                                try {
                                                    emailExtractText.add(text.get(u).getText());
                                                    toExtractEmailFromText();
                                                } catch (Exception e) {
                                                    System.out.println("Something wrong in adding item!");
                                                    e.printStackTrace();
                                                    continue;
                                                }
                                            }
                                            try {
                                                Thread.sleep(1000);
                                            } catch (Exception e) {
                                                e.getMessage();
                                            }
                                        }catch (Exception e){
                                            System.out.println("SomeThing wrong in yahoo list!");
                                            e.printStackTrace();
                                        }
                                        try {
                                            driver.findElement(By.xpath("//a[@class='next']")).click();
                                        }catch (Exception e){
                                            System.out.println("SomeThing Went wrong in yahoo click next button!");
                                            e.printStackTrace();
                                        }
                                    }
                                } catch (Exception e){
                                    System.out.println("Something went wrong in whole yahoo!");
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
                return true;
            }
        };
        progress.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    public void toExtractEmailFromText(){
        Task<Boolean> hey = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                for (int i=0; i<emailExtractText.size(); i++){
                    try {
                        Pattern pattern = Pattern.compile("[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9-.]+", Pattern.CASE_INSENSITIVE);
                        Matcher matcher = pattern.matcher(emailExtractText.get(i));
                        while(matcher.find()){
                            if (!emails.contains(matcher.group())) {
                                System.out.println(matcher.group());
                                emails.add(matcher.group());
                                int totalNumber = emails.size()+1;
                                listToAdd.add(String.valueOf(totalNumber)+".  " +matcher.group());
                                list.setItems(listToAdd);
                            }
                        }
                    }catch (Exception e){
                        System.out.println("Wrong in extracting emails ");
                        e.printStackTrace();
                    }
                }
                return true;
            }
        };
        new Thread(hey).start();
    }



    public void toSaveTxt(){
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("EmailsList.txt")));
        }catch (Exception e){
            e.getStackTrace();
        }
        for (int i=0; i<emails.size(); i++){
            try {
                writer.append(emails.get(i) + "\n");
                writer.flush();
            }catch (Exception e){
                e.getStackTrace();
            }
        }
    }
}
