package GUI;

import Controller.*;
import Shape.SingleBlock;
import com.sun.tools.javadoc.Start;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import static GUI.Theme.*;


public class iScene{
    public static Scene welcomeScene;
    public static Scene gameScene;
    public static Scene settingScene;
    public static Scene recordScene;
    public static Scene endingScene;
    public static Game theGame = Game.theGame;

    static{


        // WelcomeScene
        Button startButton = new Button("Start");
        startButton.setPrefSize(200, 80);
        startButton.setOnMouseClicked(event -> {
            Game.theGame.theStage.setTitle("游戏将在三秒后开始");
            Game.theGame.theStage.setTitle("Game Scene");
            Game.theGame.theStage.setScene(gameScene);
            Game.theGame.play();
        });

        Button settingButton = new Button("Setting");
        settingButton.setPrefSize(200, 80);
        settingButton.setOnMouseClicked(event -> {
            theGame.theStage.setScene(settingScene);
        });

        Button recordButton = new Button("Record");
        recordButton.setPrefSize(200, 80);
        recordButton.setOnMouseClicked(event -> {
            theGame.theStage.setScene(recordScene);
        });

        VBox chooseVbox = new VBox(100);
        chooseVbox.getChildren().addAll(startButton, settingButton, recordButton);

        FlowPane flowPane = new FlowPane(chooseVbox);
        flowPane.setAlignment(Pos.CENTER);

        flowPane.setPrefWidth(1280);
        flowPane.setPrefHeight(768);

        String url_Background = "file:src/Resource/Images/Tetris.jpg";
        flowPane.setStyle(
                " -fx-background-image: url(" + url_Background + "); " +
                " -fx-background-size: 120%;");
        welcomeScene = new Scene(flowPane);


        // gamePane
        HBox hb_gamePane = new HBox();

        GridPane grid_GamePane = new GridPane();
        grid_GamePane.setVgap(0);
        grid_GamePane.setHgap(0);
        String url = "file:src/Resource/Images/Grey.png";
        for(int i = 0; i < 20; i ++){
            for(int j = 0; j < 10; j++){
                SingleBlock iBtn = new SingleBlock();
                theGame.guiBlocks[i][j] = iBtn;
                iBtn.setPrefSize(32, 32);
                iBtn.setBorder(null);
                iBtn.setStyle(
                        "-fx-background-image: " +
                                "url(" + url + ");"
                );
                grid_GamePane.add(iBtn, j, i);
            }
        }

        // infoPane
        VBox vb_infoPane = new VBox();
        vb_infoPane.setSpacing(20);

        GridPane grid_nextShape = new GridPane();

        TextArea ta_infoArea = new TextArea();
        ta_infoArea.setVisible(true);
        theGame.infoArea = ta_infoArea;

        Label label = new Label();
        theGame.scoreLabel = label;
        label.setText("分数:" + String.valueOf(theGame.score));

        GridPane grid_ctrlPane = new GridPane();
        Button btn_ctrl1 = new Button("Control 1");
        Button btn_ctrl2 = new Button("Control 2");
        Button btn_ctrl3 = new Button("Control 3");
        grid_ctrlPane.add(btn_ctrl1, 1, 1);
        grid_ctrlPane.add(btn_ctrl2, 2, 1);
        grid_ctrlPane.add(btn_ctrl3, 1, 2);

        vb_infoPane.getChildren().addAll(label, ta_infoArea, grid_ctrlPane);
        hb_gamePane.getChildren().addAll(grid_GamePane, vb_infoPane);

        // Menu
        // 菜单栏
        MenuBar menuGameBar = new MenuBar();
        menuGameBar.setPrefSize(480, 20);
        // 1. "Game" 菜单
        Menu menuGame = new Menu("游戏");
        // 1.1 NewGame item
        MenuItem itemNewGame = new MenuItem("返回菜单");
        itemNewGame.setOnAction(event -> {
            theGame.theStage.setScene(welcomeScene);
        });
        // 绑定功能

        // 1.2 ReStart 选项
        MenuItem itemRestart = new MenuItem("重新开始");
        itemRestart.setOnAction(event -> {
            theGame.reStart();
        });

        // 1.3 Save 选项
        MenuItem itemSave = new MenuItem("保存游戏");
        itemSave.setOnAction(event -> {
//			System.out.println(gameStart.thisGame.getRecorder().toString());
            Game.createSave(theGame);
        });

        // 绑定到 Game
        menuGame.getItems().addAll(itemNewGame, itemRestart, itemSave);

        // 2. "Scheme" 菜单
        Menu menuScheme = new Menu("主题");

        ToggleGroup schemeChoose = new ToggleGroup();
        RadioMenuItem itemA = new RadioMenuItem("Scheme A");
        itemA.setSelected(true);
        itemA.setOnAction(event -> {
            if(itemA.isSelected()){
                theGame.setTheme(theme1);
            }
        });
        RadioMenuItem itemB = new RadioMenuItem("Scheme B");
        itemB.setOnAction(event -> {
            if(itemB.isSelected()){
                theGame.setTheme(theme2);
            }
        });
        RadioMenuItem itemC = new RadioMenuItem("Scheme C");
        itemC.setOnAction(event -> {
            if(itemC.isSelected()){
                theGame.setTheme(theme3);
            }
        });
        schemeChoose.getToggles().addAll(itemA, itemB, itemC);

        menuScheme.getItems().addAll(itemA, itemB, itemC);

        // 3. "GUI" 菜单
        Menu menuGameRule = new Menu("游戏选项");

        ToggleGroup typeChoose = new ToggleGroup();

        RadioMenuItem itemSingle = new RadioMenuItem("a");
        itemSingle.setSelected(true);
        RadioMenuItem itemSweep = new RadioMenuItem("b");

        typeChoose.getToggles().addAll(itemSingle, itemSweep);

        menuGameRule.getItems().addAll(itemSingle, itemSweep);

        menuGameBar.getMenus().addAll(menuGame, menuScheme, menuGameRule);

        VBox vb_mainGame = new VBox();
        vb_mainGame.getChildren().addAll(menuGameBar, hb_gamePane);

        gameScene = new Scene(vb_mainGame);
        gameScene.setOnKeyPressed(event -> {
            if(!theGame.isGaming){
                theGame.theStage.setScene(endingScene);
            }else{
                if(event.getCode() == KeyCode.Q){
                    if(theGame.tryLeftRotate()){
                        theGame.shapeRotateLeft();
                    }else{
                        theGame.putMessage("左旋失败\n");
                    }
                }
                if(event.getCode() == KeyCode.E){
                    if(theGame.tryRightRotate()){
                        theGame.shapeRotateRight();
                    }else{
                        theGame.putMessage("右旋失败\n");
                    }
                }
                if(event.getCode() == KeyCode.A){
                    if(theGame.tryMoveLeft()){
                        theGame.shapeMoveLeft();
                    }else{
                        theGame.putMessage("左移失败\n");
                    }
                }
                if(event.getCode() == KeyCode.D){
                    if(theGame.tryMoveRight()){
                        theGame.shapeMoveRight();
                    }else{
                        theGame.putMessage("右移失败\n");
                    }
                }
            }

            // 速降功能 bug 太多了，所以注释掉了

//            if(event.getCode() == KeyCode.S){
//                if(theGame.tryDown()){
//                    theGame.shapeMoveDown();
//                    theGame.drawBlocks();
//                }else{
//                    theGame.meltShape();
//                    if(theGame.creatCurrentShape()){
//                        theGame.drawBlocks();
//                    }else{
//                        theGame.theStage.setScene(iScene.endingScene);
//                    }
//                    theGame.drawBlocks();
//                    try{
//                        theGame.checkRows();
//                    }
//                    catch(InterruptedException e){
//                        e.printStackTrace();
//                    }
//                }
//            }
        });

        // Ending Scene
        FlowPane endingPane = new FlowPane();
        endingPane.setPrefSize(1200, 900);

        VBox vb_endingChoice = new VBox();
        vb_endingChoice.setSpacing(100);

        Button btn_BackToWelcome = new Button("返回主界面");
        btn_BackToWelcome.setPrefSize(120, 40);
        btn_BackToWelcome.setOnMouseClicked(event -> {
            theGame = new Game(theGame.theStage);
            theGame.theStage.setScene(welcomeScene);
        });

        Button btn_SaveScore = new Button("保存分数");
        btn_SaveScore.setPrefSize(120, 40);
        btn_SaveScore.setOnMouseClicked(event -> {
            System.out.println("保存分数");
        });

        Button btn_Quit = new Button("退出游戏");
        btn_Quit.setPrefSize(120, 40);
        btn_Quit.setOnMouseClicked(event -> {
            theGame.theStage.close();
        });

        Label lb_endingScore = new Label("分数:" + String.valueOf(theGame.score));

        vb_endingChoice.getChildren().addAll(lb_endingScore, btn_BackToWelcome, btn_SaveScore, btn_Quit);
        endingPane.getChildren().add(vb_endingChoice);
        endingPane.setAlignment(Pos.CENTER);
        endingScene = new Scene(endingPane);
    }


}
