// Modifications copyright (C) 2017, Baidu.com, Inc.
// Copyright 2017 The Apache Software Foundation

// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package com.baidu.palo.common;

import com.baidu.palo.thrift.TPaloBrokerService;
import com.baidu.palo.thrift.BackendService;
import com.baidu.palo.thrift.FrontendService;
import com.baidu.palo.thrift.HeartbeatService;

import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;


public class ClientPool {
    static GenericKeyedObjectPoolConfig heartbeatConfig = new GenericKeyedObjectPoolConfig();
    static int heartbeatTimeoutMs = FeConstants.heartbeat_interval_second * 1000;

    static GenericKeyedObjectPoolConfig backendConfig = new GenericKeyedObjectPoolConfig();
    static int backendTimeoutMs = 300; // 5min

    static {
        heartbeatConfig.setLifo(true);            // set Last In First Out strategy
        heartbeatConfig.setMaxIdlePerKey(2);      // (default 8)
        heartbeatConfig.setMinIdlePerKey(1);      // (default 0)
        heartbeatConfig.setMaxTotalPerKey(-1);    // (default 8)
        heartbeatConfig.setMaxTotal(-1);          // (default -1)
        heartbeatConfig.setMaxWaitMillis(500);    //  wait for the connection
    }
    
    static {
        backendConfig.setLifo(true);            // set Last In First Out strategy
        backendConfig.setMaxIdlePerKey(128);      // (default 8)
        backendConfig.setMinIdlePerKey(2);      // (default 0)
        backendConfig.setMaxTotalPerKey(-1);    // (default 8)
        backendConfig.setMaxTotal(-1);          // (default -1)
        backendConfig.setMaxWaitMillis(500);    //  wait for the connection
    }

    static GenericKeyedObjectPoolConfig brokerPoolConfig = new GenericKeyedObjectPoolConfig();
    static int brokerTimeoutMs = 300000;

    static {
        brokerPoolConfig.setLifo(true);            // set Last In First Out strategy
        brokerPoolConfig.setMaxIdlePerKey(128);      // (default 8)
        brokerPoolConfig.setMinIdlePerKey(2);      // (default 0)
        brokerPoolConfig.setMaxTotalPerKey(-1);    // (default 8)
        brokerPoolConfig.setMaxTotal(-1);          // (default -1)
        brokerPoolConfig.setMaxWaitMillis(500);    //  wait for the connection
    }

    public static GenericPool<HeartbeatService.Client> heartbeatPool =
            new GenericPool("HeartbeatService", heartbeatConfig, heartbeatTimeoutMs); 
    public static GenericPool<FrontendService.Client> frontendPool =
            new GenericPool("FrontendService", backendConfig, backendTimeoutMs);
    public static GenericPool<BackendService.Client> backendPool =
            new GenericPool("BackendService", backendConfig, backendTimeoutMs);
    public static GenericPool<TPaloBrokerService.Client> brokerPool =
            new GenericPool("TPaloBrokerService", brokerPoolConfig, brokerTimeoutMs);
}
