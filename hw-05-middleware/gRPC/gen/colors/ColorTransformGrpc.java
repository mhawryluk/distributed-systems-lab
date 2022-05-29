package colors;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.45.1)",
    comments = "Source: colors.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ColorTransformGrpc {

  private ColorTransformGrpc() {}

  public static final String SERVICE_NAME = "colors.ColorTransform";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<colors.Colors.RGBColor,
      colors.Colors.HSVColor> getRGBToHSVMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RGBToHSV",
      requestType = colors.Colors.RGBColor.class,
      responseType = colors.Colors.HSVColor.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<colors.Colors.RGBColor,
      colors.Colors.HSVColor> getRGBToHSVMethod() {
    io.grpc.MethodDescriptor<colors.Colors.RGBColor, colors.Colors.HSVColor> getRGBToHSVMethod;
    if ((getRGBToHSVMethod = ColorTransformGrpc.getRGBToHSVMethod) == null) {
      synchronized (ColorTransformGrpc.class) {
        if ((getRGBToHSVMethod = ColorTransformGrpc.getRGBToHSVMethod) == null) {
          ColorTransformGrpc.getRGBToHSVMethod = getRGBToHSVMethod =
              io.grpc.MethodDescriptor.<colors.Colors.RGBColor, colors.Colors.HSVColor>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RGBToHSV"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  colors.Colors.RGBColor.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  colors.Colors.HSVColor.getDefaultInstance()))
              .setSchemaDescriptor(new ColorTransformMethodDescriptorSupplier("RGBToHSV"))
              .build();
        }
      }
    }
    return getRGBToHSVMethod;
  }

  private static volatile io.grpc.MethodDescriptor<colors.Colors.HSVColor,
      colors.Colors.RGBColor> getHSVToRGBMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "HSVToRGB",
      requestType = colors.Colors.HSVColor.class,
      responseType = colors.Colors.RGBColor.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<colors.Colors.HSVColor,
      colors.Colors.RGBColor> getHSVToRGBMethod() {
    io.grpc.MethodDescriptor<colors.Colors.HSVColor, colors.Colors.RGBColor> getHSVToRGBMethod;
    if ((getHSVToRGBMethod = ColorTransformGrpc.getHSVToRGBMethod) == null) {
      synchronized (ColorTransformGrpc.class) {
        if ((getHSVToRGBMethod = ColorTransformGrpc.getHSVToRGBMethod) == null) {
          ColorTransformGrpc.getHSVToRGBMethod = getHSVToRGBMethod =
              io.grpc.MethodDescriptor.<colors.Colors.HSVColor, colors.Colors.RGBColor>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "HSVToRGB"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  colors.Colors.HSVColor.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  colors.Colors.RGBColor.getDefaultInstance()))
              .setSchemaDescriptor(new ColorTransformMethodDescriptorSupplier("HSVToRGB"))
              .build();
        }
      }
    }
    return getHSVToRGBMethod;
  }

  private static volatile io.grpc.MethodDescriptor<colors.Colors.ColorsOperationArgument,
      colors.Colors.HSVColor> getHSVColorsAggregateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "HSVColorsAggregate",
      requestType = colors.Colors.ColorsOperationArgument.class,
      responseType = colors.Colors.HSVColor.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<colors.Colors.ColorsOperationArgument,
      colors.Colors.HSVColor> getHSVColorsAggregateMethod() {
    io.grpc.MethodDescriptor<colors.Colors.ColorsOperationArgument, colors.Colors.HSVColor> getHSVColorsAggregateMethod;
    if ((getHSVColorsAggregateMethod = ColorTransformGrpc.getHSVColorsAggregateMethod) == null) {
      synchronized (ColorTransformGrpc.class) {
        if ((getHSVColorsAggregateMethod = ColorTransformGrpc.getHSVColorsAggregateMethod) == null) {
          ColorTransformGrpc.getHSVColorsAggregateMethod = getHSVColorsAggregateMethod =
              io.grpc.MethodDescriptor.<colors.Colors.ColorsOperationArgument, colors.Colors.HSVColor>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "HSVColorsAggregate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  colors.Colors.ColorsOperationArgument.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  colors.Colors.HSVColor.getDefaultInstance()))
              .setSchemaDescriptor(new ColorTransformMethodDescriptorSupplier("HSVColorsAggregate"))
              .build();
        }
      }
    }
    return getHSVColorsAggregateMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ColorTransformStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ColorTransformStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ColorTransformStub>() {
        @java.lang.Override
        public ColorTransformStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ColorTransformStub(channel, callOptions);
        }
      };
    return ColorTransformStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ColorTransformBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ColorTransformBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ColorTransformBlockingStub>() {
        @java.lang.Override
        public ColorTransformBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ColorTransformBlockingStub(channel, callOptions);
        }
      };
    return ColorTransformBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ColorTransformFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ColorTransformFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ColorTransformFutureStub>() {
        @java.lang.Override
        public ColorTransformFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ColorTransformFutureStub(channel, callOptions);
        }
      };
    return ColorTransformFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ColorTransformImplBase implements io.grpc.BindableService {

    /**
     */
    public void rGBToHSV(colors.Colors.RGBColor request,
        io.grpc.stub.StreamObserver<colors.Colors.HSVColor> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRGBToHSVMethod(), responseObserver);
    }

    /**
     */
    public void hSVToRGB(colors.Colors.HSVColor request,
        io.grpc.stub.StreamObserver<colors.Colors.RGBColor> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHSVToRGBMethod(), responseObserver);
    }

    /**
     */
    public void hSVColorsAggregate(colors.Colors.ColorsOperationArgument request,
        io.grpc.stub.StreamObserver<colors.Colors.HSVColor> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHSVColorsAggregateMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getRGBToHSVMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                colors.Colors.RGBColor,
                colors.Colors.HSVColor>(
                  this, METHODID_RGBTO_HSV)))
          .addMethod(
            getHSVToRGBMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                colors.Colors.HSVColor,
                colors.Colors.RGBColor>(
                  this, METHODID_HSVTO_RGB)))
          .addMethod(
            getHSVColorsAggregateMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                colors.Colors.ColorsOperationArgument,
                colors.Colors.HSVColor>(
                  this, METHODID_HSVCOLORS_AGGREGATE)))
          .build();
    }
  }

  /**
   */
  public static final class ColorTransformStub extends io.grpc.stub.AbstractAsyncStub<ColorTransformStub> {
    private ColorTransformStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ColorTransformStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ColorTransformStub(channel, callOptions);
    }

    /**
     */
    public void rGBToHSV(colors.Colors.RGBColor request,
        io.grpc.stub.StreamObserver<colors.Colors.HSVColor> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRGBToHSVMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void hSVToRGB(colors.Colors.HSVColor request,
        io.grpc.stub.StreamObserver<colors.Colors.RGBColor> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHSVToRGBMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void hSVColorsAggregate(colors.Colors.ColorsOperationArgument request,
        io.grpc.stub.StreamObserver<colors.Colors.HSVColor> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHSVColorsAggregateMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ColorTransformBlockingStub extends io.grpc.stub.AbstractBlockingStub<ColorTransformBlockingStub> {
    private ColorTransformBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ColorTransformBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ColorTransformBlockingStub(channel, callOptions);
    }

    /**
     */
    public colors.Colors.HSVColor rGBToHSV(colors.Colors.RGBColor request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRGBToHSVMethod(), getCallOptions(), request);
    }

    /**
     */
    public colors.Colors.RGBColor hSVToRGB(colors.Colors.HSVColor request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHSVToRGBMethod(), getCallOptions(), request);
    }

    /**
     */
    public colors.Colors.HSVColor hSVColorsAggregate(colors.Colors.ColorsOperationArgument request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHSVColorsAggregateMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ColorTransformFutureStub extends io.grpc.stub.AbstractFutureStub<ColorTransformFutureStub> {
    private ColorTransformFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ColorTransformFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ColorTransformFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<colors.Colors.HSVColor> rGBToHSV(
        colors.Colors.RGBColor request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRGBToHSVMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<colors.Colors.RGBColor> hSVToRGB(
        colors.Colors.HSVColor request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHSVToRGBMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<colors.Colors.HSVColor> hSVColorsAggregate(
        colors.Colors.ColorsOperationArgument request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHSVColorsAggregateMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_RGBTO_HSV = 0;
  private static final int METHODID_HSVTO_RGB = 1;
  private static final int METHODID_HSVCOLORS_AGGREGATE = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ColorTransformImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ColorTransformImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_RGBTO_HSV:
          serviceImpl.rGBToHSV((colors.Colors.RGBColor) request,
              (io.grpc.stub.StreamObserver<colors.Colors.HSVColor>) responseObserver);
          break;
        case METHODID_HSVTO_RGB:
          serviceImpl.hSVToRGB((colors.Colors.HSVColor) request,
              (io.grpc.stub.StreamObserver<colors.Colors.RGBColor>) responseObserver);
          break;
        case METHODID_HSVCOLORS_AGGREGATE:
          serviceImpl.hSVColorsAggregate((colors.Colors.ColorsOperationArgument) request,
              (io.grpc.stub.StreamObserver<colors.Colors.HSVColor>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ColorTransformBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ColorTransformBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return colors.Colors.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ColorTransform");
    }
  }

  private static final class ColorTransformFileDescriptorSupplier
      extends ColorTransformBaseDescriptorSupplier {
    ColorTransformFileDescriptorSupplier() {}
  }

  private static final class ColorTransformMethodDescriptorSupplier
      extends ColorTransformBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ColorTransformMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ColorTransformGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ColorTransformFileDescriptorSupplier())
              .addMethod(getRGBToHSVMethod())
              .addMethod(getHSVToRGBMethod())
              .addMethod(getHSVColorsAggregateMethod())
              .build();
        }
      }
    }
    return result;
  }
}
