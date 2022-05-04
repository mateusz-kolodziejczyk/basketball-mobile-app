package org.mk.basketballmanager.enums

enum class Position {
    PointGuard {
        override fun toString(): String {
            return "Point Guard"
        }
    },
    ShootingGuard{
        override fun toString(): String {
            return "Shooting Guard"
        }
    },
    SmallForward{
        override fun toString(): String {
            return "Small Forward"
        }
    },
    PowerForward{
        override fun toString(): String {
            return "Power Forward"
        }
    },
    Center{
        override fun toString(): String {
            return "Center"
        }
    },
    None{
        override fun toString(): String {
            return "None"
        }
    },
}