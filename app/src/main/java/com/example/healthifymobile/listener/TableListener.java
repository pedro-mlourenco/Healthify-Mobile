package com.example.healthifymobile.listener;

import com.example.healthifymobile.modelo.TableReserveAPI;

public interface TableListener{
    void onTableReserve(TableReserveAPI reserve,String mesa);
    void onTableFree(TableReserveAPI reserve,String mesa);
    void onSeatedClient(TableReserveAPI reserve,String mesa);
}
