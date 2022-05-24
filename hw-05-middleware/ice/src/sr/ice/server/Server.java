package sr.ice.server;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;

public class Server {
	public void t1(String[] args) {
		int status = 0;
		Communicator communicator = null;

		try	{
			// 1. Inicjalizacja ICE - utworzenie communicatora
			communicator = Util.initialize(args);

			// 2. Konfiguracja adaptera
			// METODA 1 (polecana produkcyjnie): Konfiguracja adaptera Adapter1 jest w pliku konfiguracyjnym podanym jako parametr uruchomienia serwera
			//ObjectAdapter adapter = communicator.createObjectAdapter("Adapter1");

			// METODA 2 (niepolecana, dopuszczalna testowo): Konfiguracja adaptera Adapter1 jest w kodzie źródłowym
			//ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("Adapter1", "tcp -h 127.0.0.2 -p 10000");
			//ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("Adapter1", "tcp -h 127.0.0.2 -p 10000 : udp -h 127.0.0.2 -p 10000");
			ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("Adapter1", "tcp -h 127.0.0.1 -p 10000 -z : udp -h 127.0.0.1 -p 10000 -z");

			// 3. Stworzenie serwanta/serwantów
			Lamp lamp1 = new Lamp();
			RadioSpeaker radioSpeaker1 = new RadioSpeaker();
			BTSpeaker btSpeaker1 = new BTSpeaker();
			Camera camera1 = new Camera();

			// 4. Dodanie wpisów do tablicy ASM, skojarzenie nazwy obiektu (Identity) z serwantem
			adapter.add(lamp1, new Identity("lamp1", "lamp"));
			adapter.add(radioSpeaker1, new Identity("radioSpeaker1", "radio_speaker"));
			adapter.add(btSpeaker1, new Identity("btSpeaker1", "bt_speaker"));
			adapter.add(camera1, new Identity("camera1", "camera"));

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
		Server app = new Server();
		app.t1(args);
	}
}
