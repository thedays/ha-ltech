package com.ltech.smarthome.ui.scene;

import com.ltech.smarthome.base.BaseViewModel;

/* loaded from: classes4.dex */
public class StateConditionVM extends BaseViewModel {

    public static class Condition {
        private int max;
        private int min;
        private int operator;
        private boolean sel;
        private int setValue;
        private int type;

        public Condition(int type, int min, int max) {
            this.operator = 1;
            this.type = type;
            this.max = max;
            this.min = min;
        }

        public Condition(int type, int min, int max, boolean sel) {
            this.operator = 1;
            this.type = type;
            this.max = max;
            this.min = min;
            this.sel = sel;
        }

        public Condition(int type, int operator, int min, int max, boolean sel) {
            this.type = type;
            this.max = max;
            this.min = min;
            this.sel = sel;
            this.operator = operator;
        }

        public Condition(int type, int operator, int min, int max, int setValue, boolean sel) {
            this.type = type;
            this.max = max;
            this.min = min;
            this.sel = sel;
            this.operator = operator;
            this.setValue = setValue;
        }

        public int getOperator() {
            return this.operator;
        }

        public void setOperator(int operator) {
            this.operator = operator;
        }

        public int getType() {
            return this.type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public boolean isSel() {
            return this.sel;
        }

        public void setSel(boolean sel) {
            this.sel = sel;
        }

        public int getMax() {
            return this.max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public int getMin() {
            return this.min;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public int getSetValue() {
            return this.setValue;
        }

        public void setSetValue(int setValue) {
            this.setValue = setValue;
        }
    }
}