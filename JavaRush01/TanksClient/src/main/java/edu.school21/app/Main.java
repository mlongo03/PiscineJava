package edu.school21.app;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import edu.school21.components.Bullet;
import edu.school21.components.LifeBar;
import edu.school21.components.Modal;
import edu.school21.components.Player;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.FontPosture;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;

public class Main extends Application {

    private final String FIELD_IMAGE = "/assets/images/field.png";
    private final String BORDER_IMAGE = "/assets/images/border.png";
    private final String ENEMY_IMAGE = "/assets/images/enemy.png";
    private final String ENEMYBULLET_IMAGE = "/assets/images/enemyBullet.png";
    private final String FAIL_IMAGE = "/assets/images/fail.png";
    private final String LIFE_IMAGE = "/assets/images/life.png";
    private final String PLAYER_IMAGE = "/assets/images/player.png";
    private final String PLAYERBULLET_IMAGE = "/assets/images/playerBullet.png";

    private final int WIDTH = 1042;
    private final int HEIGHT = 1042;
    private final int PLAYER_W = 80;
    private final int PLAYER_H = 100;
    private final int TEXT_SIZE = 30;

    private Pane root = new Pane();

    private int shoots = 0;
    private int misses = 0;
    private int hits = 0;

    private Player player = new Player((WIDTH - PLAYER_W) / 2, HEIGHT - (2 * PLAYER_H), 80, 100,
            new Image(PLAYER_IMAGE, 80, 100, true, false, false), WIDTH, new Image(FAIL_IMAGE));

    private Player enemy = new Player((WIDTH - PLAYER_W) / 2, PLAYER_H, 80, 100,
            new Image(ENEMY_IMAGE, 80, 100, true, false, false), WIDTH, new Image(FAIL_IMAGE));

    private LifeBar playerBar = new LifeBar(100, 10, HEIGHT - 30, new Image(BORDER_IMAGE), new Image(LIFE_IMAGE), root);
    private LifeBar enemyBar = new LifeBar(100, WIDTH - 150, 30, new Image(BORDER_IMAGE),
            new Image(LIFE_IMAGE), root);
    private List<Bullet> playerBullet = new ArrayList();
    private List<Bullet> enemyBullet = new ArrayList();

    private static Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private double shootDelay = 0;

    private String serverUrl;
    private int serverPort;
    private int hP;

    private Label l1;
    private Label l2;
    private Label l3;
    private TextField tf1;
    private TextField tf2;
    private TextField tf3;
    private Button btn;
    private Text text;

    private void setModalInit() {
        root.setStyle("-fx-background-color: green;");
        l1 = new Label("SERVER URL");
        l2 = new Label("SERVER PORT");
        l3 = new Label("HEALT POINTS");
        l2.setLayoutY(25);
        l3.setLayoutY(50);
        tf1 = new TextField();
        tf1.setLayoutX(100);
        tf2 = new TextField();
        tf2.setLayoutY(25);
        tf2.setLayoutX(100);
        tf3 = new TextField();
        tf3.setLayoutY(50);
        tf3.setLayoutX(100);

        root.getChildren().add(l1);
        root.getChildren().add(l2);
        root.getChildren().add(l3);
        root.getChildren().add(tf1);
        root.getChildren().add(tf2);
        root.getChildren().add(tf3);

        text = new Text();
        text.setX(WIDTH / 3);
        text.setY(HEIGHT / 2 - TEXT_SIZE);
        text.setFont(Font.font("Abyssinica SIL", FontWeight.BOLD, FontPosture.REGULAR, TEXT_SIZE));
        text.setFill(Color.YELLOW);
        text.setStroke(Color.BLACK);
        text.setStrokeWidth(1);
        text.setText("PRESS PLAY TO START");
        root.getChildren().add(text);

        btn = new Button("PLAY");
        btn.setLayoutX(WIDTH / 2);
        btn.setLayoutY(HEIGHT / 2);
        root.getChildren().add(btn);
    }

    private Parent createContent() {
        root.setPrefSize(WIDTH, HEIGHT);
        setModalInit();

        btn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent arg) {

                serverUrl = tf1.getText();
                try {
                    serverPort = Integer.parseInt(tf2.getText());
                } catch (Exception e) {
                    serverPort = 4242;
                }
                try {
                    hP = Integer.parseInt(tf3.getText());
                } catch (Exception e) {
                    hP = 100;
                }
                try {
                    System.out.println(serverUrl);
                    socket = new Socket(serverUrl, serverPort);
                    dos = new DataOutputStream(socket.getOutputStream());
                    dis = new DataInputStream(socket.getInputStream());
                    receiveMessageFromServer(root);
                    playerBar.setHp(hP);
                    playerBar.setVisible();
                    enemyBar.setVisible();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    e.printStackTrace();
                    System.exit(-1);
                }
                root.getChildren().remove(l1);
                root.getChildren().remove(l2);
                root.getChildren().remove(l3);
                root.getChildren().remove(tf1);
                root.getChildren().remove(tf2);
                root.getChildren().remove(tf3);
                root.getChildren().remove(text);
                root.getChildren().remove(btn);
                root.setBackground(setBackground());
                root.getChildren().add(player);
                root.getChildren().add(enemy);

                AnimationTimer timer = new AnimationTimer() {
                    @Override
                    public void handle(long t) {
                        update();
                    }
                };
                timer.start();
            }
        });

        return root;
    }

    private void update() {
        shootDelay += 0.016;
        if (enemy.getToken()) {
            enemy.setToken(false);
            enemyShoot();
        }
        player.move();
        if (player.isDead() || enemy.isDead()) {
            new Modal(shoots, hits, misses, root, WIDTH, HEIGHT, TEXT_SIZE);
            return;
        }
        try {
            dos.writeUTF("M:" + player.getTranslateX() + "\n");
        } catch (Exception e) {
        }
        if (playerBullet.size() == 0 && enemyBullet.size() == 0)
            return;
        for (int i = 0; i < playerBullet.size(); i++) {
            Bullet s = playerBullet.get(i);
            s.moveUp();
            if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                enemy.hit();
                hits++;
                enemyBar.setHp(enemy.getLife());
                try {
                    dos.writeUTF("H\n");
                } catch (Exception ex) {
                }
                root.getChildren().remove(s);
                playerBullet.remove(s);
            } else if (s.getTranslateY() < 0) {
                misses++;
                root.getChildren().remove(s);
                playerBullet.remove(s);
            }
        }
        playerBullet.forEach(s -> {
        });
        for (int i = 0; i < enemyBullet.size(); i++) {
            Bullet s = enemyBullet.get(i);
            s.moveDown();
            if (s.getBoundsInParent().intersects(player.getBoundsInParent())) {
                root.getChildren().remove(s);
                enemyBullet.remove(s);
            } else if (s.getTranslateY() > HEIGHT) {
                root.getChildren().remove(s);
                enemyBullet.remove(s);
            }
        }
    }

    private void playerShoot() {
        if (shootDelay > 0.4 && player.getStart()) {
            shootDelay = 0;
            Bullet temp = new Bullet((int) player.getTranslateX() + 40, (int) player.getTranslateY(), 5, 11,
                    new Image(PLAYERBULLET_IMAGE, 5, 11, true, false, false),
                    HEIGHT);
            playerBullet.add(temp);
            root.getChildren().add(temp);
            shoots++;
            try {
                dos.writeUTF("S\n");
            } catch (Exception ex) {
            }
        }
    }

    private void enemyShoot() {
        Bullet temp = new Bullet((int) enemy.getTranslateX() + 40, (int) enemy.getTranslateY() + 100, 5, 11,
                new Image(ENEMYBULLET_IMAGE, 5, 11, true, false, false),
                HEIGHT);
        enemyBullet.add(temp);
        root.getChildren().add(temp);
    }

    private Background setBackground() {
        Image img = new Image(FIELD_IMAGE);
        ImagePattern imgP = new ImagePattern(img, 0, 0, 1042, 1042, false);
        BackgroundFill bgFill = new BackgroundFill(imgP, CornerRadii.EMPTY, Insets.EMPTY);
        Background bg = new Background(bgFill);
        return bg;
    }

    private void receiveMessageFromServer(Pane r) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (socket.isConnected()) {
                    try {
                        String message = dis.readUTF();
                        String[] split = message.split(":");
                        switch (split[0]) {
                            case "M":
                                enemy.moveTo(Double.parseDouble(split[1]));
                                break;
                            case "S\n":
                                enemy.setToken(true);
                                break;
                            case "H\n":
                                player.hit();
                                playerBar.setHp(player.getLife());
                                if (player.isDead()) {
                                    dos.writeUTF("W\n");
                                    dos.writeUTF("R:" + shoots + ":" + hits + ":" + misses);
                                    player.setStart(false);
                                    enemy.setStart(false);
                                    break;
                                }
                                break;
                            case "W\n":
                                dos.writeUTF("R:" + shoots + ":" + hits + ":" + misses);
                                player.setStart(false);
                                enemy.setStart(false);
                                break;
                            case "start\n":
                                player.setStart(true);
                                enemy.setStart(true);
                                break;
                        }
                    } catch (Exception e) {
                        break;
                    }
                }
            }
        }).start();
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(createContent());
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT:
                    player.setVel(-3);
                    if (e.getCode() == KeyCode.SPACE) {
                        playerShoot();
                    }
                    break;
                case RIGHT:
                    player.setVel(3);
                    if (e.getCode() == KeyCode.SPACE) {
                        playerShoot();
                    }
                    break;
                case SPACE:
                    playerShoot();
                    break;
            }
        });
        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case LEFT:
                    try {
                        dos.writeUTF("L\n");
                    } catch (Exception ex) {
                    }
                    player.setVel(0);
                    break;
                case RIGHT:
                    try {
                        dos.writeUTF("R\n");
                    } catch (Exception ex) {
                    }
                    player.setVel(0);
                    break;
            }
        });
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        try {
            socket.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
