package weather;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Thread.NORM_PRIORITY;

/**
 * Created by Julia on 20.10.2016.
 */
public class Server extends Thread{
    private Socket socket;
    private int num;

    public static void main(String[] args) {
        int port = 2000;
        try {
            int i = 0; // счётчик подключений

            // привинтить сокет на локалхост, порт 3128
            ServerSocket ss = new ServerSocket(port); // создаем сокет сервера и привязываем его к вышеуказанному порту
            System.out.println("Ожидаем клиентов");

            // слушаем порт
            while (true) {
                // ждём нового подключения, после чего запускаем обработку клиента
                // в новый вычислительный поток и увеличиваем счётчик на единичку
                new Server(i, ss.accept());
                System.out.println("Клиент подключен");
                i++;
            }
        } catch (Exception e) {
            System.out.println("init error: " + e); // вывод исключений
            e.printStackTrace();
        }
    }

    public Server(int num, Socket s)
    {
        // копируем данные
        this.num = num;
        this.socket = s;

        // и запускаем новый вычислительный поток (см. ф-ю run())
        setDaemon(true);
        setPriority(NORM_PRIORITY);
        start();
    }

    public void run()
    {
        try
        {
            // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиенту.
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            String line = null;
            line = in.readUTF(); // ожидаем пока клиент пришлет строку текста.
            System.out.println("Клиент запросил " + line);
            String line1 = Weather.weather(line);
            System.out.println("Отсылаем клиенту " + line1);
            out.writeUTF(line1); // отсылаем клиенту обратно ту самую строку текста.
            out.flush(); // заставляем поток закончить передачу данных.
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}
//"2123260"