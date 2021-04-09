package com.example.test_http;

import java.util.List;

public class ManagerReturnData {
    private List<FundManagerBean> searchFundManagers;

    public List<FundManagerBean> getSearchFundManagers() {
        return searchFundManagers;
    }

    public void setSearchFundManagers(List<FundManagerBean> searchFundManagers) {
        this.searchFundManagers = searchFundManagers;
    }
}
