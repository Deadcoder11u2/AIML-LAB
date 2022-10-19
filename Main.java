import java.util.*;
import java.io.*;

public class Main {
    static boolean vis[][][][][];
    static int N;
    static StringBuilder sb = new StringBuilder();
    public static void main(String args[]) {
        N = Integer.parseInt(args[0]);
        vis = new boolean[N+1][N+1][N+1][N+1][2];
        go(N, N, 0, 0, true);
        System.out.println(sb);
    }

    static boolean go(int lc, int lm, int rc, int rm, boolean boatIsLeft) {
        if(lc + lm == 0) {
            return true;
        }

        if((lc > lm && lm != 0)|| (rc > rm && rm != 0)) {
            return false; 
        }

        if(vis[lc][lm][rc][rm][boatIsLeft ? 1 : 0]) return false;

        vis[lc][lm][rc][rm][boatIsLeft ? 1 : 0] = true;
        boolean ans = false;
        if(boatIsLeft) {
            if(lc >= 2) {
                ans |= go(lc-2, lm, rc+2, rm, !boatIsLeft);
            }
            if(lm >= 2) {
                ans |= go(lc, lm-2, rc, rm+2, !boatIsLeft);
            }
            if(lm >= 1 && lc >= 2) {
                ans |= go(lc-1, lm-1, rc+1, rm+1, !boatIsLeft);
            }
        }
        else {
            if(rc >= 1) {
                ans |= go(lc+1, lm, rc-1, rm, !boatIsLeft);
            }
            if(rm >= 1) {
                ans |= go(lc, lm+1, rc, rm-1, !boatIsLeft);
            }
            if(rm >= 2) {
                ans |= go(lc, lm+2, rc, rm-2, !boatIsLeft);
            }
            if(rc >= 2) {
                ans |= go(lc+2, lm, rc-2, rm, !boatIsLeft);
            }
            if(rc >= 1 && rm >= 1) {
                ans |= go(lc+1, lm+1, rc-1, rm-1, !boatIsLeft);
            }
        }
        if(ans) {
            sb.append("M".repeat(rm)+"_".repeat(N-rm) + " " + "C".repeat(rc)+"_".repeat(N-rc) + " ================== " + "M".repeat(lm)+"_".repeat(N-lm) + " " + "C".repeat(lc)+"_".repeat(N-lc) + "\n");
        }
        return ans;
    }

}
