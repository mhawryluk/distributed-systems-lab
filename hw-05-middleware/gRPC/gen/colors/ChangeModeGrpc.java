package colors;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.45.1)",
    comments = "Source: colors.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ChangeModeGrpc {

  private ChangeModeGrpc() {}

  public static final String SERVICE_NAME = "colors.ChangeMode";

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
    if ((getRGBToHSVMethod = ChangeModeGrpc.getRGBToHSVMethod) == null) {
      synchronized (ChangeModeGrpc.class) {
        if ((getRGBToHSVMethod = ChangeModeGrpc.getRGBToHSVMethod) == null) {
          ChangeModeGrpc.getRGBToHSVMethod = getRGBToHSVMethod =
              io.grpc.MethodDescriptor.<colors.Colors.RGBColor, colors.Colors.HSVColor>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RGBToHSV"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  colors.Colors.RGBColor.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  colors.Colors.HSVColor.getDefaultInstance()))
              .setSchemaDescriptor(new ChangeModeMethodDescriptorSupplier("RGBToHSV"))
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
    if ((getHSVToRGBMethod = ChangeModeGrpc.getHSVToRGBMethod) == null) {
      synchronized (ChangeModeGrpc.class) {
        if ((getHSVToRGBMethod = ChangeModeGrpc.getHSVToRGBMethod) == null) {
          ChangeModeGrpc.getHSVToRGBMethod = getHSVToRGBMethod =
              io.grpc.MethodDescriptor.<colors.Colors.HSVColor, colors.Colors.RGBColor>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "HSVToRGB"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  colors.Colors.HSVColor.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  colors.Colors.RGBColor.getDefaultInstance()))
              .setSchemaDescriptor(new ChangeModeMethodDescriptorSupplier("HSVToRGB"))
              .build();
        }
      }
    }
    return getHSVToRGBMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ChangeModeStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChangeModeStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChangeModeStub>() {
        @java.lang.Override
        public ChangeModeStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChangeModeStub(channel, callOptions);
        }
      };
    return ChangeModeStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ChangeModeBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChangeModeBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChangeModeBlockingStub>() {
        @java.lang.Override
        public ChangeModeBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChangeModeBlockingStub(channel, callOptions);
        }
      };
    return ChangeModeBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ChangeModeFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChangeModeFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChangeModeFutureStub>() {
        @java.lang.Override
        public ChangeModeFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChangeModeFutureStub(channel, callOptions);
        }
      };
    return ChangeModeFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ChangeModeImplBase implements io.grpc.BindableService {

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
          .build();
    }
  }

  /**
   */
  public static final class ChangeModeStub extends io.grpc.stub.AbstractAsyncStub<ChangeModeStub> {
    private ChangeModeStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChangeModeStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChangeModeStub(channel, callOptions);
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
  }

  /**
   */
  public static final class ChangeModeBlockingStub extends io.grpc.stub.AbstractBlockingStub<ChangeModeBlockingStub> {
    private ChangeModeBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChangeModeBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChangeModeBlockingStub(channel, callOptions);
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
  }

  /**
   */
  public static final class ChangeModeFutureStub extends io.grpc.stub.AbstractFutureStub<ChangeModeFutureStub> {
    private ChangeModeFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChangeModeFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChangeModeFutureStub(channel, callOptions);
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
  }

  private static final int METHODID_RGBTO_HSV = 0;
  private static final int METHODID_HSVTO_RGB = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ChangeModeImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ChangeModeImplBase serviceImpl, int methodId) {
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

  private static abstract class ChangeModeBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ChangeModeBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return colors.Colors.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ChangeMode");
    }
  }

  private static final class ChangeModeFileDescriptorSupplier
      extends ChangeModeBaseDescriptorSupplier {
    ChangeModeFileDescriptorSupplier() {}
  }

  private static final class ChangeModeMethodDescriptorSupplier
      extends ChangeModeBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ChangeModeMethodDescriptorSupplier(String methodName) {
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
      synchronized (ChangeModeGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ChangeModeFileDescriptorSupplier())
              .addMethod(getRGBToHSVMethod())
              .addMethod(getHSVToRGBMethod())
              .build();
        }
      }
    }
    return result;
  }
}
