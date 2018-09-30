#pragma once

#include "Zero_K_Core.h"
#include "Game.h"
#include "Camera.h"

inline int KEY_DOWN(int key) {
	return GetAsyncKeyState(key) & 0x8000;
}