package com.btlab.devops.module.ids.framework.rpc.config;

import com.btlab.devops.module.infra.api.config.ConfigApi;
import com.btlab.devops.module.infra.api.file.FileApi;
import com.btlab.devops.module.infra.api.websocket.WebSocketSenderApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(value = "IdsRpcConfiguration", proxyBeanMethods = false)
@EnableFeignClients(clients = {FileApi.class, WebSocketSenderApi.class, ConfigApi.class})
public class RpcConfiguration {
}
