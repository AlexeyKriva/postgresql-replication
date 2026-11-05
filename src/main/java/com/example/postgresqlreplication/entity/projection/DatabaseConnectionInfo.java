package com.example.postgresqlreplication.entity.projection;

import java.net.Inet4Address;

public interface DatabaseConnectionInfo {
    Inet4Address getInetServerAddr();

    Integer getInetServerPort();

    Boolean getPgIsInRecovery();

    Integer getPgBackendPid();

    String getTransactionReadOnly();

    String getDefaultTransactionReadOnly();
}
