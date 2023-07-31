package edu.school21.tank.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import edu.school21.tank.models.Game;
import edu.school21.tank.models.Player;
import edu.school21.tank.repositories.GameRepository;
import edu.school21.tank.repositories.GameRepositoryImpl;
import edu.school21.tank.repositories.PlayerRepository;

public class Server {
    private static GameRepository gRepo;
    private static PlayerRepository pRepo;
    private List<ClientHandler> clients = new ArrayList<ClientHandler>();
    private Map<Long, ClientHandler> socketMap = new HashMap<Long, ClientHandler>();
    private int nClient = 0;

    public Server(int port, GameRepository gRepo, PlayerRepository pRepo) {
        this.gRepo = gRepo;
        this.pRepo = pRepo;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);

            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("client connected: " + client.getInetAddress().getHostAddress());
                ClientHandler clientSocket = new ClientHandler(client);
                addClient(clientSocket);
                new Thread(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void addClient(ClientHandler client) {
        clients.add(client);
        Player p = new Player(null, 0, 0, 0);
        pRepo.save(p);
        Long id = p.getID();
        client.setPlayerId(id);
        socketMap.put(id, client);
        Optional<Game> g = gRepo.findWaitingGame();
        if (g.isEmpty()) {
            Game newG = new Game(null, p, null, new Timestamp(System.currentTimeMillis()), 0);
            gRepo.save(newG);
            client.setGameId(newG.getID());
        } else {
            Game found = g.get();
            client.setGameId(found.getID());
            found.setPlayer2(p);
            ClientHandler p1 = socketMap.get(found.getPlayer1().getID());
            p1.setOther(client);
            client.setOther(p1);
            p1.startGame();
            client.startGame();
            found.setStatus(1);
            gRepo.update(found);
        }
        nClient++;
    }

    private void remClient(ClientHandler client) {
        clients.remove(client);
        nClient--;
    }

    private static class ClientHandler implements Runnable {
        private final Socket clienSocket;
        private Long playerId;
        private Long gameId;
        DataOutputStream out = null;
        DataInputStream in = null;
        ClientHandler other = null;

        public ClientHandler(Socket socket) {
            this.clienSocket = socket;
            try {
                out = new DataOutputStream(socket.getOutputStream());
                in = new DataInputStream(socket.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void startGame() {
            try {
                out.writeUTF("start\n");
            } catch (Exception e) {
            }
        }

        public void setOther(ClientHandler other) {
            this.other = other;
        }

        public void print(String message) {
            if (other != null) {
                try {
                    out.writeUTF(message);
                } catch (Exception e) {
                }
            }
        }

        public void saveResult(String[] strs, int status) {
            Optional<Player> p = pRepo.findById(playerId);
            if (!p.isEmpty() && strs.length == 4) {
                Player toSave = p.get();
                toSave.setShots(Integer.parseInt(strs[1]));
                toSave.setHits(Integer.parseInt(strs[2]));
                toSave.setMisses(Integer.parseInt(strs[3]));
                pRepo.update(toSave);
            }
            Optional<Game> g = gRepo.findById(gameId);
            if (!g.isEmpty()) {
                Game gToSave = g.get();
                gToSave.setStatus(status);
                gRepo.update(gToSave);
            }
        }

        public void setPlayerId(Long id) {
            this.playerId = id;
        }

        public void setGameId(Long id) {
            this.gameId = id;
        }

        public void run() {
            try {
                String line;
                while ((line = in.readUTF()) != null) {
                    if (other != null) {
                        if (line.startsWith("R:")) {
                            System.out.println(line);
                            saveResult(line.split(":"), 2);
                            break;
                        } else
                            other.print(line);
                    }
                }
                System.out.println("client disconnected: " + this.clienSocket);
            } catch (IOException e) {
                System.out.println("client disconnected: " + this.clienSocket);
                Optional<Game> g = gRepo.findById(gameId);
                if (!g.isEmpty()) {
                    Game gToSave = g.get();
                    gToSave.setStatus(3);
                    gRepo.update(gToSave);
                }
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                        clienSocket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}