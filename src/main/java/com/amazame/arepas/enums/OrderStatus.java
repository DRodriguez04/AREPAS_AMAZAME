package com.amazame.arepas.enums;

public enum OrderStatus {

    CREATED {
        @Override
        public boolean canTransitionTo(OrderStatus newStatus) {
            return newStatus == PAID || newStatus == CANCELLED;
        }
    },

    PAID {
        @Override
        public boolean canTransitionTo(OrderStatus newStatus) {
            return newStatus == PREPARING || newStatus == CANCELLED;
        }
    },

    PREPARING {
        @Override
        public boolean canTransitionTo(OrderStatus newStatus) {
            return newStatus == ON_THE_WAY;
        }
    },

    ON_THE_WAY {
        @Override
        public boolean canTransitionTo(OrderStatus newStatus) {
            return newStatus == DELIVERED;
        }
    },

    DELIVERED {
        @Override
        public boolean canTransitionTo(OrderStatus newStatus) {
            return false;
        }
    },

    CANCELLED {
        @Override
        public boolean canTransitionTo(OrderStatus newStatus) {
            return false;
        }
    };

    public abstract boolean canTransitionTo(OrderStatus newStatus);
}

