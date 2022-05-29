package server;

import colors.ColorTransformGrpc;
import colors.Colors;

import java.awt.*;

public class ColorTransform extends ColorTransformGrpc.ColorTransformImplBase {

	@Override
	public void rGBToHSV(Colors.RGBColor request, io.grpc.stub.StreamObserver<Colors.HSVColor> responseObserver) {
		System.out.println("RGB TO HSV request (" + request + ")");


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

	@Override
	public void hSVColorsAggregate(Colors.ColorsOperationArgument request, io.grpc.stub.StreamObserver<Colors.HSVColor> responseObserver) {
		System.out.println("Color aggregation request (" + request + ")");

		Colors.HSVColor result = switch (request.getOperation()) {
			case AVERAGE -> {
				float hSum = 0.f;
				float sSum = 0.f;
				float vSum = 0.f;

				for (Colors.HSVColor color : request.getColorsList()) {
					hSum += color.getH();
					sSum += color.getS();
					vSum += color.getV();
				}

				yield Colors.HSVColor.newBuilder()
						.setH(hSum/request.getColorsCount())
						.setS(sSum/request.getColorsCount())
						.setV(vSum/request.getColorsCount())
						.build();
			}

			case MAX -> {
				float hMax = 0.f;
				float sMax = 0.f;
				float vMax = 0.f;

				for (Colors.HSVColor color : request.getColorsList()) {
					hMax = Math.max(color.getH(), hMax);
					sMax = Math.max(color.getS(), sMax);
					vMax = Math.max(color.getV(), vMax);
				}

				yield Colors.HSVColor.newBuilder()
						.setH(hMax)
						.setS(sMax)
						.setV(vMax)
						.build();
			}
			default -> throw new IllegalStateException("Unexpected value: " + request.getOperation());
		};

		responseObserver.onNext(result);
		responseObserver.onCompleted();
	}
}
