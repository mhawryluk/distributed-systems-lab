package sr.ice.server;

import SmartHome.Device;
import SmartHome.DeviceId;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;


public class Server {
	public void run(String[] args) {
		int status = 0;
		Communicator communicator = null;

		DeviceId[] devices = new DeviceId[args.length - 3];

		try	{
			// 1. Inicjalizacja ICE - utworzenie communicatora
			communicator = Util.initialize(new String[]{args[0]});

			// 2. Konfiguracja adaptera
			// METODA 1 (polecana produkcyjnie): Konfiguracja adaptera Adapter1 jest w pliku konfiguracyjnym podanym jako parametr uruchomienia serwera
			ObjectAdapter adapter = communicator.createObjectAdapter(args[1]);

			int port = Integer.parseInt(args[2]);

			// 3. Stworzenie serwanta/serwantów
			for (int i = 3; i < args.length; i++) {
				String[] deviceSpec = args[i].split("/");
				String category = deviceSpec[0];
				String name = deviceSpec[1];

				devices[i-3] = new DeviceId(name, category, port);

				System.out.println("category: " + category + " | name: " + name);

				Device device = switch (category) {
					case "lamp" -> new Lamp(name);
					case "radio_speaker" -> new RadioSpeaker(name);
					case "bt_speaker" -> new BTSpeaker(name);
					case "camera" -> new Camera(name);
					default -> throw new IllegalArgumentException("not supported category");
				};

				// 4. Dodanie wpisów do tablicy ASM, skojarzenie nazwy obiektu (Identity) z serwantem
				adapter.add(device, new Identity(name, category));
			}

			HomeInfo.setDevices(devices);
			HomeInfo homeInfoServant = new HomeInfo();
			adapter.add(homeInfoServant, new Identity("home_info", "home_info"));

			// 5. Aktywacja adaptera i wejście w pętlę przetwarzania żądań
			adapter.activate();
			
			System.out.println("Entering event processing loop...");
			communicator.waitForShutdown();
		}
		catch (Exception e) {
			System.err.println(e);
			status = 1;
		}

		if (communicator != null) {
			try {
				communicator.destroy();
			}
			catch (Exception e) {
				System.err.println(e);
				status = 1;
			}
		}
		System.exit(status);
	}

	public static void main(String[] args) {
		Server server = new Server();
		server.run(args);
	}
}
