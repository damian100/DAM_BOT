package damian.dam.bot;

import java.awt.*;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DMain extends ListenerAdapter{

    public static JDA jda;
    public static String check_data = "none";
    public static boolean isStop = false;
    public String predict_str = "";
    private WebElement predict_round;
    private WebElement predict_1,predict_2,predict_3,predict_4,predict_5,predict_6,predict_7,predict_8,predict_9,predict_10,predict_11;
    private WebElement predict_result_1,predict_result_2,predict_result_3,predict_result_4,predict_result_5;
    private boolean pre_end1, pre_end2, pre_end3, pre_end4, pre_end5;
    public static void main(String[] args) throws LoginException, InterruptedException{
        Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/chromedriver");  // 현재 package의

        // WebDriver 경로 설정
        System.setProperty("webdriver.chrome.driver", path.toString());
        JDABuilder jb = new JDABuilder(AccountType.BOT);
        jb.setAutoReconnect(true);
        jb.setStatus(OnlineStatus.DO_NOT_DISTURB);
        jb.setToken(args[0]);
        jb.addEventListeners(new DMain());

        jb.build();
        // WebDriver 옵션 설정
        /*
        URL uri = null;
        try {
            uri = new URL(null, "https://www.weather.go.kr/pews/pews.html", new sun.net.www.protocol.https.Handler());

            javax.net.ssl.HttpsURLConnection connection = (javax.net.ssl.HttpsURLConnection) uri.openConnection();
            connection.setRequestProperty("user-agent", "xpath Tester");*/
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        try {
            Message msg = event.getMessage();
            MessageChannel mc = event.getChannel();
            String str = msg.getContentRaw();
            String order = str.substring(0, 7);
            JDA jda = event.getJDA();
            String a = "";
            if(order.equals("!GetAva")) {
                mc.sendMessage("노무현").queue((message) ->
                        message.editMessageFormat("명빡",a).queue());
                /*String tag = str.substring(8);
                User usr = jda.getUserByTag(tag);

                mc.sendMessage(usr.getAvatarUrl()).queue();
                System.out.println("성공");*/
                mc.sendMessage(event.getChannel().toString()).queue();
                System.out.println("RunDriver");
                mc.sendMessage("성공").queue();
                RunDriver1(event);
            }
            if(msg.equals("!run")) {
                RunDriver1(event);
                System.out.println("RunDriver");
            }
            if(msg.equals("!stop")) {
                isStop = true;
            }
        }catch(Exception e) {}
    }

    public void RunDriver1(MessageReceivedEvent event) {

        Message msg = event.getMessage();
        MessageChannel mc = event.getChannel();
        JDA jda = event.getJDA();

        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--start-maximized");            // 전체화면으로 실행
        options.addArguments("--disable-popup-blocking");    // 팝업 무시
        options.addArguments("--disable-default-apps");     // 기본앱 사용안함
        // WebDriver 객체 생성
        ChromeDriver driver = new ChromeDriver(options);
        // 빈 탭 생성
        driver.executeScript("window.open('about:blank','_blank');");
        // 탭 목록 가져오기
        List<String> tabs = new ArrayList<String>(driver.getWindowHandles());

        // 첫번째 탭으로 전환
        driver.switchTo().window(tabs.get(0));
        // 웹페이지 요청
        driver.get("http://ntry.com/scores/powerball/main.php");
        // 웹페이지에서 글제목 가져오기
        /*WebElement page1_title = driver.findElementByXPath("/html/body/div[2]/div[2]/div[2]/div[7]/div[3]/table/thead/tr[1]/th[1]");
        if( page1_title != null  ) {
            System.out.println( page1_title.getText() );
        }*/
        //List<WebElement> page1_title = driver.findElementsByXPath("//*[@id=\"round-history\"]");
        WebElement check_data_ele = driver.findElementByXPath("//*[@id=\"round-history\"]/tr[" + 1 + "]/td[" + 1 + "]");
        check_data = check_data_ele.getText();
        String[] mInfo = new String[15];
        //for (int idx = 0; idx < 15; idx++) {
        //System.out.println(idx);
        /*
        WebElement pb_round_data = driver.findElementByXPath("//*[@id=\"round-history\"]/tr[" + (1) + "]/td[" + 1 + "]");
        WebElement pb_left_line = driver.findElementByXPath("//*[@id=\"round-history\"]/tr[" + (1) + "]/td[" + 2 + "]");
        WebElement pb_cell_border = driver.findElementByXPath("//*[@id=\"round-history\"]/tr[" + (1) + "]/td[" + 3 + "]/span");
        WebElement pb_cell_oddeven = driver.findElementByXPath("//*[@id=\"round-history\"]/tr[" + (1) + "]/td[" + 4 + "]/span");
        WebElement pb_cell_unover = driver.findElementByXPath("//*[@id=\"round-history\"]/tr[" + (1) + "]/td[" + 5 + "]/span");
        WebElement num_left_line = driver.findElementByXPath("//*[@id=\"round-history\"]/tr[" + (1) + "]/td[" + 6 + "]");
        WebElement num_cell_border = driver.findElementByXPath("//*[@id=\"round-history\"]/tr[" + (1) + "]/td[" + 7 + "]/span");
        WebElement num_cell_border2 = driver.findElementByXPath("//*[@id=\"round-history\"]/tr[" + (1) + "]/td[" + 8 + "]/span");
        WebElement num_cell_border3 = driver.findElementByXPath("//*[@id=\"round-history\"]/tr[" + (1) + "]/td[" + 9 + "]/span");
        WebElement num_cell_oddeven = driver.findElementByXPath("//*[@id=\"round-history\"]/tr[" + (1) + "]/td[" + 10 + "]/span");
        WebElement num_cell_unover = driver.findElementByXPath("//*[@id=\"round-history\"]/tr[" + (1) + "]/td[" + 11 + "]/span");
        */
        //System.out.println(pb_round_data.getText());
        //mInfo[idx] = ""+pb_round_data.getText()+"회차 | 파볼[결과:"+pb_left_line.getText()+"구간:"+pb_cell_border.getText()+"홀짝:"+pb_cell_oddeven.getText()+"언오버:"+pb_cell_unover.getText()+"]  숫자[결과:"+num_left_line.getText()+"합:"+num_cell_border.getText()+"구간:"+num_cell_border2.getText()+"대중소:"+num_cell_border3.getText()+"홀짝:"+num_cell_oddeven.getText()+"언오버:"+num_cell_unover.getText()+"]";
        //}
        //EmbedBuilder eb = new EmbedBuilder();
        //eb.setTitle("예측결과");
        //eb.addField(pb_round_data.getText() + " 회차 | ", "파볼[결과:" + pb_left_line.getText() + "구간:" + pb_cell_border.getText() + "홀짝:" + pb_cell_oddeven.getText() + "언오버:" + pb_cell_unover.getText() + "]  숫자[결과:" + num_left_line.getText() + "합:" + num_cell_border.getText() + "구간:" + num_cell_border2.getText() + "대중소:" + num_cell_border3.getText() + "홀짝:" + num_cell_oddeven.getText() + "언오버:" + num_cell_unover.getText() + "]", true);
        //eb.setColor(Color.YELLOW);
        //mc.sendMessage(eb.build()).queue();
        SendPredictValue(driver, event, tabs);
        while (true) {
            if (isStop) {
                isStop = false;

                break;
            }
            WebElement check_data_ele2 = driver.findElementByXPath("//*[@id=\"round-history\"]/tr[" + 1 + "]/td[" + 1 + "]");
            if (check_data.equals(check_data_ele2.getText())) {

                try {
                    driver.get("http://ntry.com/scores/powerball/main.php");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Check3");
                check_data = check_data_ele2.getText();
            /*
            for (int idx = 0; idx < 15; idx++) {
                //System.out.println(idx);
                pb_round_data = driver.findElementByXPath("//*[@id=\"round-history\"]/tr[" + (idx + 1) + "]/td[" + 1 + "]");
                pb_left_line = driver.findElementByXPath("//*[@id=\"round-history\"]/tr[" + (idx + 1) + "]/td[" + 2 + "]");
                pb_cell_border = driver.findElementByXPath("//*[@id=\"round-history\"]/tr[" + (idx + 1) + "]/td[" + 3 + "]/span");
                pb_cell_oddeven = driver.findElementByXPath("//*[@id=\"round-history\"]/tr[" + (idx + 1) + "]/td[" + 4 + "]/span");//홀짝
                pb_cell_unover = driver.findElementByXPath("//*[@id=\"round-history\"]/tr[" + (idx + 1) + "]/td[" + 5 + "]/span");//언오버
                num_left_line = driver.findElementByXPath("//*[@id=\"round-history\"]/tr[" + (idx + 1) + "]/td[" + 6 + "]");
                num_cell_border = driver.findElementByXPath("//*[@id=\"round-history\"]/tr[" + (idx + 1) + "]/td[" + 7 + "]/span");
                num_cell_border2 = driver.findElementByXPath("//*[@id=\"round-history\"]/tr[" + (idx + 1) + "]/td[" + 8 + "]/span");
                num_cell_border3 = driver.findElementByXPath("//*[@id=\"round-history\"]/tr[" + (idx + 1) + "]/td[" + 9 + "]/span");//대중소
                num_cell_oddeven = driver.findElementByXPath("//*[@id=\"round-history\"]/tr[" + (idx + 1) + "]/td[" + 10 + "]/span");//홀짝
                num_cell_unover = driver.findElementByXPath("//*[@id=\"round-history\"]/tr[" + (idx + 1) + "]/td[" + 11 + "]/span");//언오버
                //System.out.println(pb_round_data.getText());
                mInfo[idx] = "" + pb_round_data.getText() + "회차 | 파볼[결과:" + pb_left_line.getText() + "구간:" + pb_cell_border.getText() + "홀짝:" + pb_cell_oddeven.getText() + "언오버:" + pb_cell_unover.getText() + "]  숫자[결과:" + num_left_line.getText() + "합:" + num_cell_border.getText() + "구간:" + num_cell_border2.getText() + "대중소:" + num_cell_border3.getText() + "홀짝:" + num_cell_oddeven.getText() + "언오버:" + num_cell_unover.getText() + "]";
            }*//*
            String send_str2 = "";
            for (int idx = 0; idx < 15; idx++) {
                send_str2 = send_str2 + "\n" + mInfo[idx];
            }
            mc.sendMessage(send_str2).queue();*/
                //mc.editMessageById("733538923296325703",send_str2);
                try {
                    WebElement pb_cell_oddeven = driver.findElementByXPath("//*[@id=\"round-history\"]/tr[" + (1) + "]/td[" + 4 + "]/span");//홀짝
                    WebElement pb_cell_unover = driver.findElementByXPath("//*[@id=\"round-history\"]/tr[" + (1) + "]/td[" + 5 + "]/span");//언오버

                    WebElement num_cell_border3 = driver.findElementByXPath("//*[@id=\"round-history\"]/tr[" + (1) + "]/td[" + 9 + "]/span");//대중소
                    WebElement num_cell_oddeven = driver.findElementByXPath("//*[@id=\"round-history\"]/tr[" + (1) + "]/td[" + 10 + "]/span");//홀짝
                    WebElement num_cell_unover = driver.findElementByXPath("//*[@id=\"round-history\"]/tr[" + (1) + "]/td[" + 11 + "]/span");//언오버
                    /*pre_end1 = pb_cell_oddeven.equals(predict_result_1);
                    pre_end2 = pb_cell_unover.equals(predict_result_2);
                    pre_end3 = num_cell_oddeven.equals(predict_result_3);
                    pre_end4 = num_cell_unover.equals(predict_result_4);
                    pre_end5 = num_cell_border3.equals(predict_result_5);
                    String pre_end1_str = "";
                    String pre_end2_str = "";
                    String pre_end3_str = "";
                    String pre_end4_str = "";
                    String pre_end5_str = "";
                    if (pre_end1){
                        pre_end1_str = "적중";
                    }else{
                        pre_end1_str = "미적";
                    }
                    if (pre_end2){
                        pre_end2_str = "적중";
                    }else{
                        pre_end2_str = "미적";
                    }
                    if (pre_end3){
                        pre_end3_str = "적중";
                    }else{
                        pre_end3_str = "미적";
                    }
                    if (pre_end4){
                        pre_end4_str = "적중";
                    }else{
                        pre_end4_str = "미적";
                    }
                    if (pre_end5){
                        pre_end5_str = "적중";
                    }else{
                        pre_end5_str = "미적";
                    }
                    TextChannel textChannel = event.getGuild().getTextChannelById("733224187447083041");
                    MessageHistory mh = new MessageHistory(textChannel);
                    List<Message> msgs = mh.retrievePast(1).complete();
                    textChannel.deleteMessages(msgs);*/



                    Thread.sleep(5000);
                    SendPredictValue(driver, event, tabs);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
        driver.close();
    }
    public void SendPredictValue(ChromeDriver driver, MessageReceivedEvent event, List<String> tabs) {
        MessageChannel mc = event.getChannel();
        driver.switchTo().window(tabs.get(1));
        driver.get("http://ntry.com/stats/powerball/total.php?days=60");
        predict_round = driver.findElementByXPath("//*[@id=\"analysis\"]/div[5]/table/tbody/tr[1]/th/div");
        predict_1 = driver.findElementByXPath("//*[@id=\"analysis\"]/div[5]/table/tbody/tr[1]/td[1]/span");
         predict_2 = driver.findElementByXPath("//*[@id=\"analysis\"]/div[5]/table/tbody/tr[1]/td[2]/span");
         predict_3 = driver.findElementByXPath("//*[@id=\"analysis\"]/div[5]/table/tbody/tr[1]/td[3]/span");
         predict_4 = driver.findElementByXPath("//*[@id=\"analysis\"]/div[5]/table/tbody/tr[1]/td[4]/span");
         predict_5 = driver.findElementByXPath("//*[@id=\"analysis\"]/div[5]/table/tbody/tr[1]/td[5]/span");
         predict_6 = driver.findElementByXPath("//*[@id=\"analysis\"]/div[5]/table/tbody/tr[1]/td[6]/span");
         predict_7 = driver.findElementByXPath("//*[@id=\"analysis\"]/div[5]/table/tbody/tr[1]/td[7]/span");
         predict_8 = driver.findElementByXPath("//*[@id=\"analysis\"]/div[5]/table/tbody/tr[1]/td[8]/span");
         predict_9 = driver.findElementByXPath("//*[@id=\"analysis\"]/div[5]/table/tbody/tr[1]/td[9]/span");
         predict_10 = driver.findElementByXPath("//*[@id=\"analysis\"]/div[5]/table/tbody/tr[1]/td[10]/span");
         predict_11 = driver.findElementByXPath("//*[@id=\"analysis\"]/div[5]/table/tbody/tr[1]/td[11]/span");
         predict_result_1 = driver.findElementByXPath("//*[@id=\"analysis\"]/div[5]/table/tbody/tr[1]/td[12]/span[1]");
         predict_result_2 = driver.findElementByXPath("//*[@id=\"analysis\"]/div[5]/table/tbody/tr[1]/td[12]/span[2]");
         predict_result_3 = driver.findElementByXPath("//*[@id=\"analysis\"]/div[5]/table/tbody/tr[1]/td[12]/span[3]");
         predict_result_4 = driver.findElementByXPath("//*[@id=\"analysis\"]/div[5]/table/tbody/tr[1]/td[12]/span[4]");
         predict_result_5 = driver.findElementByXPath("//*[@id=\"analysis\"]/div[5]/table/tbody/tr[1]/td[12]/span[5]");
        predict_str = predict_round + " 회차  [" + predict_result_1.getText() + "|" + predict_result_2.getText() + "|" + predict_result_3.getText() + "|" + predict_result_4.getText() + "|" + predict_result_5.getText() + "]";

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("예측결과");
        eb.addField(predict_round.getText() + " 회차","[" + predict_result_1.getText() + "|" + predict_result_2.getText() + "|" + predict_result_3.getText() + "|" + predict_result_4.getText() + "|" + predict_result_5.getText() + "]",true);
        eb.setColor(Color.YELLOW);
        TextChannel textChannel = event.getGuild().getTextChannelById("733224187447083041");
        textChannel.sendMessage(eb.build()).queue();
        //mc.sendMessage(predict_str).queue();
        driver.switchTo().window(tabs.get(0));
    }
}