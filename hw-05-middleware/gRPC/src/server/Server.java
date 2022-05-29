package server;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;


public class Server {
	private static final Logger logger = Logger.getLogger(Server.class.getName());

	private int port = 50051;
	private io.grpc.Server server;

	private void start() throws IOException {

		server = NettyServerBuilder.forPort(port).executor(Executors.newFixedThreadPool(16))
				.addService(new ColorTransform())
				.addService(ProtoReflectionService.newInstance())
				.build()
				.start();

		logger.info("Server started, listening on " + port);
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.err.println("*** shutting down gRPC server since JVM is shutting down");
			Server.this.stop();
			System.err.println("*** server shut down");
		}));
	}

	private void stop() {
		if (server != null) server.shutdown();
	}

	private void blockUntilShutdown() throws InterruptedException {
		if (server != null) server.awaitTermination();
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		final Server server = new Server();
		server.start();
		server.blockUntilShutdown();
	}
}
