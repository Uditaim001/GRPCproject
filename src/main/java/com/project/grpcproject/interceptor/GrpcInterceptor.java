package com.project.grpcproject.interceptor;

import io.grpc.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;




public class GrpcInterceptor implements ServerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(GrpcInterceptor.class);
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        logger.info("Call to method:{}", serverCall.getMethodDescriptor().getFullMethodName());
        logger.info("Metadata: {}",metadata);
        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(serverCallHandler.startCall(serverCall, metadata)) {
            @Override
            public void onMessage(ReqT message) {
                logger.info("Request:{}",message);
                super.onMessage(message);
            }
            @Override
            public void onComplete() {
                logger.info("Call completed");
                super.onComplete();
            }

            @Override
            public void onHalfClose() {
                logger.info("Requests Finished");
                super.onHalfClose();
            }
        };
    }
}
