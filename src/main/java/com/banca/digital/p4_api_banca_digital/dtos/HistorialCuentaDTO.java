package com.banca.digital.p4_api_banca_digital.dtos;

import java.util.List;

public class HistorialCuentaDTO {

    private String CuentaId;
    private double balance;
    private int currentPage;
    private int totalPage;
    private int papeSize;
    private List<OperacionCuentaDTO> operacionesCuentaDTOS;

    //Getters And Setters
    public String getCuentaId() {
        return CuentaId;
    }

    public void setCuentaId(String cuentaId) {
        CuentaId = cuentaId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPapeSize() {
        return papeSize;
    }

    public void setPapeSize(int papeSize) {
        this.papeSize = papeSize;
    }

    public List<OperacionCuentaDTO> getOperacionesCuentaDTOS() {
        return operacionesCuentaDTOS;
    }

    public void setOperacionesCuentaDTOS(List<OperacionCuentaDTO> operacionesCuentaDTOS) {
        this.operacionesCuentaDTOS = operacionesCuentaDTOS;
    }
}
