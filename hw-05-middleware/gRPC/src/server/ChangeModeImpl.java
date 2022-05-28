package server;

import colors.ChangeModeGrpc.ChangeModeImplBase;
import colors.Colors;

import java.awt.*;

public class ChangeModeImpl extends ChangeModeImplBase {

	@Override
	public void rGBToHSV(Colors.RGBColor request, io.grpc.stub.StreamObserver<Colors.HSVColor> responseObserver) {
		System.out.println("RGB TO HSV request (" + request + ")");

//		responseObserver.onError(io.grpc.Status.INVALID_ARGUMENT.withDescription("Bad arguments").asRuntimeException());

		float[] hsv = new float[3];
		int r = request.getR();
		int g = request.getG();
		int b = request.getB();

		Color.RGBtoHSB(r, g, b, hsv);

		Colors.HSVColor result = Colors.HSVColor.newBuilder()
				.setH(hsv[0])
				.setS(hsv[1])
				.setV(hsv[2])
				.build();

		responseObserver.onNext(result);
		responseObserver.onCompleted();
	}

	@Override
	public void hSVToRGB(Colors.HSVColor request, io.grpc.stub.StreamObserver<Colors.RGBColor> responseObserver) {
		System.out.println("HSV TO RGB request (" + request + ")");

//		responseObserver.onError(io.grpc.Status.INVALID_ARGUMENT.withDescription("Bad arguments").asRuntimeException());
		float h = request.getH();
		float s = request.getS();
		float v = request.getV();

		int rgb = Color.HSBtoRGB(h, s, v);

		int r = (rgb>>16)&0xFF;
		int g = (rgb>>8)&0xFF;
		int b = rgb&0xFF;

		Colors.RGBColor result = Colors.RGBColor.newBuilder().
				setR(r).
				setG(g).
				setB(b).
				build();

		responseObserver.onNext(result);
		responseObserver.onCompleted();
	}

//	@Override
//	public void add(Argument request,
//			io.grpc.stub.StreamObserver<sr.grpc.gen.ArithmeticOpResult> responseObserver) {
//		System.out.println("addRequest (" + request.getArg1() + ", " + request.getArg2() +")");
//		int val = request.getArg1() + request.getArg2();
//		ArithmeticOpResult result = ArithmeticOpResult.newBuilder().setRes(val).build();
//		if(request.getArg1() > 100 && request.getArg2() > 100) try { Thread.sleep(5000); } catch(InterruptedException ex) { }
//		responseObserver.onNext(result);
//		responseObserver.onCompleted();
//	}
//
//	@Override
//	public void subtract(sr.grpc.gen.ArithmeticOpArguments request,
//			io.grpc.stub.StreamObserver<sr.grpc.gen.ArithmeticOpResult> responseObserver)
//	{
//		System.out.println("subtractRequest (" + request.getArg1() + ", " + request.getArg2() +")");
//
//		responseObserver.onError(io.grpc.Status.INVALID_ARGUMENT.withDescription("Bad arguments").asRuntimeException());
//
//		int val = request.getArg1() - request.getArg2();
//		ArithmeticOpResult result = ArithmeticOpResult.newBuilder().setRes(val).build();
//		responseObserver.onNext(result);
//		responseObserver.onCompleted();
//	}
//
//	@Override
//	public void multiply(sr.grpc.gen.MultiplyArgs request, io.grpc.stub.StreamObserver<sr.grpc.gen.ArithmeticOpResult> responseObserver) {
//		int val = 1;
//		for (int num : request.getNumbersList()) {
//			val *= num;
//		}
//
//		ArithmeticOpResult result = ArithmeticOpResult.newBuilder().setRes(val).build();
//		responseObserver.onNext(result);
//		responseObserver.onCompleted();
//	}

}
