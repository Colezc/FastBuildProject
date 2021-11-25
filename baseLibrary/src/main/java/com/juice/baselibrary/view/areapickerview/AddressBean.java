package com.juice.baselibrary.view.areapickerview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AddressBean implements   Serializable{

    private String label;
    private String value;
    private boolean status;
    private int count;
    private List<CityBean> children = new ArrayList<>();

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<CityBean> getChildren() {
        return children;
    }

    public void setChildren(List<CityBean> children) {
        this.children = children;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public class CityBean {
        private String label;
        private String value;
        private boolean status;
        private int count;
        private List<AreaBean> children = new ArrayList<>();

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public List<AreaBean> getChildren() {
            return children;
        }

        public void setChildren(List<AreaBean> children) {
            this.children = children;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public class AreaBean {
            private String label;
            private String value;
            private boolean status;
            private int count;
            private List<VillageBean> children = new ArrayList<>();

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public boolean isStatus() {
                return status;
            }

            public void setStatus(boolean status) {
                this.status = status;
            }

            public void setChildren(List<VillageBean> children) {
                this.children = children;
            }
            public List<VillageBean> getChildren() {
                return children;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public class VillageBean{
                private String label;
                private String value;
                private boolean status;
                private int count;

                public String getLabel() {
                    return label;
                }

                public void setLabel(String label) {
                    this.label = label;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public boolean isStatus() {
                    return status;
                }

                public void setStatus(boolean status) {
                    this.status = status;
                }

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }
            }

        }

    }

}
