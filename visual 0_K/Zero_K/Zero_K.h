#pragma once

#include "Zero_K_LIBS.h"

inline int KEY_DOWN( int key ){
    return GetAsyncKeyState( key ) & 0x8000;
}

#include "Game.h"
#include "Globals.h"
#include "mathFunctional.h"
#include "Functional.h"
#include "Camera.h"

