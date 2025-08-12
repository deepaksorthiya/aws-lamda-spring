package com.example;


import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.amazonaws.services.lambda.runtime.logging.LogLevel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class StreamLambdaHandler implements RequestStreamHandler {
    private static SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

    static {
        try {
            handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(Application.class);
        } catch (ContainerInitializationException e) {
            // if we fail here. We re-throw the exception to force another cold start
            e.printStackTrace();
            throw new RuntimeException("Could not initialize Spring Boot application", e);
        }
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
            throws IOException {
        LambdaLogger logger = context.getLogger();
        logger.log("Request received In Function: {}" + context.getFunctionName(), LogLevel.INFO);
        handler.proxyStream(inputStream, outputStream, context);
    }
}

//class AsynchronousLambdaHandler implements RequestHandler<AwsProxyRequest, AwsProxyResponse> {
//    private SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;
//
//    public AsynchronousLambdaHandler() throws ContainerInitializationException {
//        handler = (SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse>)
//                new SpringBootProxyHandlerBuilder()
//                        .springBootApplication(Application.class)
//                        .asyncInit()
//                        .buildAndInitialize();
//    }
//
//    @Override
//    public AwsProxyResponse handleRequest(AwsProxyRequest input, Context context) {
//        return handler.proxy(input, context);
//    }
//}