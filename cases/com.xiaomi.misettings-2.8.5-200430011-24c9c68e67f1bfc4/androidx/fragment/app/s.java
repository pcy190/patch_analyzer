// Decompiled by JEB v3.19.1.202005071620

package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater.Factory2;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.t;
import androidx.lifecycle.u;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

final class s extends k implements LayoutInflater.Factory2 {
    static class a {
        public final Animation a;
        public final Animator b;

        a(Animator arg2) {
            this.a = null;
            this.b = arg2;
            if(arg2 != null) {
                return;
            }
            throw new IllegalStateException("Animator cannot be null");
        }

        a(Animation arg2) {
            this.a = arg2;
            this.b = null;
            if(arg2 != null) {
                return;
            }
            throw new IllegalStateException("Animation cannot be null");
        }
    }

    static class b extends AnimationSet implements Runnable {
        private final ViewGroup a;
        private final View b;
        private boolean c;
        private boolean d;
        private boolean e;

        b(@NonNull Animation arg2, @NonNull ViewGroup arg3, @NonNull View arg4) {
            super(false);
            this.e = true;
            this.a = arg3;
            this.b = arg4;
            this.addAnimation(arg2);
            this.a.post(this);
        }

        @Override  // android.view.animation.AnimationSet
        public boolean getTransformation(long arg3, Transformation arg5) {
            this.e = true;
            if(this.c) {
                return this.d ^ 1;
            }
            if(!super.getTransformation(arg3, arg5)) {
                this.c = true;
                t.a(this.a, this);
            }
            return 1;
        }

        @Override  // android.view.animation.Animation
        public boolean getTransformation(long arg3, Transformation arg5, float arg6) {
            this.e = true;
            if(this.c) {
                return this.d ^ 1;
            }
            if(!super.getTransformation(arg3, arg5, arg6)) {
                this.c = true;
                t.a(this.a, this);
            }
            return 1;
        }

        @Override
        public void run() {
            if(!this.c && (this.e)) {
                this.e = false;
                this.a.post(this);
                return;
            }
            this.a.endViewTransition(this.b);
            this.d = true;
        }
    }

    static final class c {
        final androidx.fragment.app.k.b a;
        final boolean b;

    }

    static class d {
        public static final int[] a;

        static {
            d.a = new int[]{0x1010003, 0x10100D0, 0x10100D1};
        }
    }

    interface e {
        boolean a(ArrayList arg1, ArrayList arg2);
    }

    static class f implements androidx.fragment.app.Fragment.c {
        final boolean a;
        final androidx.fragment.app.a b;
        private int c;

        f(androidx.fragment.app.a arg1, boolean arg2) {
            this.a = arg2;
            this.b = arg1;
        }

        @Override  // androidx.fragment.app.Fragment$c
        public void a() {
            ++this.c;
        }

        @Override  // androidx.fragment.app.Fragment$c
        public void b() {
            --this.c;
            if(this.c != 0) {
                return;
            }
            this.b.s.B();
        }

        public void c() {
            this.b.s.a(this.b, this.a, false, false);
        }

        public void d() {
            int v1 = 0;
            int v0 = this.c <= 0 ? 0 : 1;
            s v3 = this.b.s;
            int v4 = v3.i.size();
            while(v1 < v4) {
                Fragment v5 = (Fragment)v3.i.get(v1);
                v5.a(null);
                if(v0 != 0 && (v5.J())) {
                    v5.ha();
                }
                ++v1;
            }
            this.b.s.a(this.b, this.a, v0 ^ 1, true);
        }

        public boolean e() {
            return this.c == 0;
        }
    }

    boolean A;
    boolean B;
    ArrayList C;
    ArrayList D;
    ArrayList E;
    Bundle F;
    SparseArray G;
    ArrayList H;
    private v I;
    Runnable J;
    static boolean c = false;
    static final Interpolator d;
    static final Interpolator e;
    ArrayList f;
    boolean g;
    int h;
    final ArrayList i;
    final HashMap j;
    ArrayList k;
    ArrayList l;
    private OnBackPressedDispatcher m;
    private final androidx.activity.c n;
    ArrayList o;
    ArrayList p;
    ArrayList q;
    private final CopyOnWriteArrayList r;
    int s;
    j t;
    g u;
    Fragment v;
    @Nullable
    Fragment w;
    boolean x;
    boolean y;
    boolean z;

    static {
        s.d = new DecelerateInterpolator(2.5f);
        s.e = new DecelerateInterpolator(1.5f);
    }

    s() {
        this.h = 0;
        this.i = new ArrayList();
        this.j = new HashMap();
        this.n = new l(this, false);
        this.r = new CopyOnWriteArrayList();
        this.s = 0;
        this.F = null;
        this.G = null;
        this.J = new m(this);
    }

    Parcelable A() {
        ArrayList v1_1;
        this.H();
        this.G();
        this.r();
        this.y = true;
        BackStackState[] v2 = null;
        if(this.j.isEmpty()) {
            return null;
        }
        ArrayList v3 = new ArrayList(this.j.size());
        Iterator v1 = this.j.values().iterator();
        int v4 = 0;
        int v5 = 0;
        while(v1.hasNext()) {
            Object v6 = v1.next();
            Fragment v6_1 = (Fragment)v6;
            if(v6_1 == null) {
                continue;
            }
            if(v6_1.s == this) {
                FragmentState v5_1 = new FragmentState(v6_1);
                v3.add(v5_1);
                if(v6_1.b > 0 && v5_1.m == null) {
                    v5_1.m = this.q(v6_1);
                    String v8 = v6_1.i;
                    if(v8 != null) {
                        Fragment v8_1 = (Fragment)this.j.get(v8);
                        if(v8_1 == null) {
                            this.a(new IllegalStateException("Failure saving state: " + v6_1 + " has target not in fragment manager: " + v6_1.i));
                            throw null;
                        }
                        if(v5_1.m == null) {
                            v5_1.m = new Bundle();
                        }
                        this.a(v5_1.m, "android:target_state", v8_1);
                        int v8_2 = v6_1.j;
                        if(v8_2 != 0) {
                            v5_1.m.putInt("android:target_req_state", v8_2);
                            goto label_75;
                            this.a(new IllegalStateException("Failure saving state: " + v6_1 + " has target not in fragment manager: " + v6_1.i));
                            throw null;
                        }
                    }
                }
                else {
                    v5_1.m = v6_1.c;
                }
            label_75:
                if(s.c) {
                    Log.v("FragmentManager", "Saved state of " + v6_1 + ": " + v5_1.m);
                }
                v5 = 1;
                continue;
            }
            this.a(new IllegalStateException("Failure saving state: active " + v6_1 + " was removed from the FragmentManager"));
            throw null;
        }
        if(v5 == 0) {
            if(s.c) {
                Log.v("FragmentManager", "saveAllState: no fragments!");
            }
            return null;
        }
        int v0 = this.i.size();
        if(v0 > 0) {
            v1_1 = new ArrayList(v0);
            for(Object v5_2: this.i) {
                Fragment v5_3 = (Fragment)v5_2;
                v1_1.add(v5_3.f);
                if(v5_3.s == this) {
                    if(!s.c) {
                        continue;
                    }
                    Log.v("FragmentManager", "saveAllState: adding fragment (" + v5_3.f + "): " + v5_3);
                    continue;
                }
                this.a(new IllegalStateException("Failure saving state: active " + v5_3 + " was removed from the FragmentManager"));
                throw null;
            }
        }
        else {
            v1_1 = null;
        }
        ArrayList v0_2 = this.k;
        if(v0_2 != null) {
            int v0_3 = v0_2.size();
            if(v0_3 > 0) {
                v2 = new BackStackState[v0_3];
                while(v4 < v0_3) {
                    v2[v4] = new BackStackState(((androidx.fragment.app.a)this.k.get(v4)));
                    if(s.c) {
                        Log.v("FragmentManager", "saveAllState: adding back stack #" + v4 + ": " + this.k.get(v4));
                    }
                    ++v4;
                }
            }
        }
        FragmentManagerState v0_4 = new FragmentManagerState();
        v0_4.a = v3;
        v0_4.b = v1_1;
        v0_4.c = v2;
        Fragment v1_2 = this.w;
        if(v1_2 != null) {
            v0_4.d = v1_2.f;
        }
        v0_4.e = this.h;
        return v0_4;
    }

    void B() {
        synchronized(this) {
            int v1 = 0;
            int v0_1 = this.H == null || (this.H.isEmpty()) ? 0 : 1;
            if(this.f != null && this.f.size() == 1) {
                v1 = 1;
            }
            if(v0_1 != 0 || v1 != 0) {
                this.t.g().removeCallbacks(this.J);
                this.t.g().post(this.J);
                this.I();
            }
            return;
        }
    }

    void C() {
        for(Object v1: this.j.values()) {
            Fragment v1_1 = (Fragment)v1;
            if(v1_1 == null) {
                continue;
            }
            this.n(v1_1);
        }
    }

    private void D() {
        this.j.values().removeAll(Collections.singleton(null));
    }

    private void E() {
        if(!this.x()) {
            return;
        }
        throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
    }

    private void F() {
        this.g = false;
        this.D.clear();
        this.C.clear();
    }

    private void G() {
        for(Object v1: this.j.values()) {
            Fragment v3 = (Fragment)v1;
            if(v3 == null) {
                continue;
            }
            if(v3.i() != null) {
                int v4 = v3.C();
                View v1_1 = v3.i();
                Animation v2 = v1_1.getAnimation();
                if(v2 != null) {
                    v2.cancel();
                    v1_1.clearAnimation();
                }
                v3.a(null);
                this.a(v3, v4, 0, 0, false);
                continue;
            }
            if(v3.j() == null) {
                continue;
            }
            v3.j().end();
        }
    }

    private void H() {
        if(this.H != null) {
            while(!this.H.isEmpty()) {
                ((f)this.H.remove(0)).d();
            }
        }
    }

    private void I() {
        boolean v1 = true;
        if(this.f != null && !this.f.isEmpty()) {
            this.n.a(true);
            return;
        }
        androidx.activity.c v0 = this.n;
        if(this.s() <= 0 || !this.i(this.v)) {
            v1 = false;
        }
        v0.a(v1);
    }

    private int a(ArrayList arg8, ArrayList arg9, int arg10, int arg11, a.d.d arg12) {
        int v0 = arg11 - 1;
        int v1 = arg11;
        while(v0 >= arg10) {
            androidx.fragment.app.a v2 = (androidx.fragment.app.a)arg8.get(v0);
            boolean v3 = ((Boolean)arg9.get(v0)).booleanValue();
            if((v2.d()) && !v2.a(arg8, v0 + 1, arg11)) {
                if(this.H == null) {
                    this.H = new ArrayList();
                }
                f v4 = new f(v2, v3);
                this.H.add(v4);
                v2.a(v4);
                if(v3) {
                    v2.b();
                }
                else {
                    v2.b(false);
                }
                --v1;
                if(v0 != v1) {
                    arg8.remove(v0);
                    arg8.add(v1, v2);
                }
                this.a(arg12);
            }
            --v0;
        }
        return v1;
    }

    static a a(float arg1, float arg2) {
        AlphaAnimation v0 = new AlphaAnimation(arg1, arg2);
        v0.setInterpolator(s.e);
        v0.setDuration(220L);
        return new a(v0);
    }

    static a a(float arg11, float arg12, float arg13, float arg14) {
        AnimationSet v0 = new AnimationSet(false);
        ScaleAnimation v1 = new ScaleAnimation(arg11, arg12, arg11, arg12, 1, 0.5f, 1, 0.5f);
        v1.setInterpolator(s.d);
        v1.setDuration(220L);
        v0.addAnimation(v1);
        AlphaAnimation v1_1 = new AlphaAnimation(arg13, arg14);
        v1_1.setInterpolator(s.e);
        v1_1.setDuration(220L);
        v0.addAnimation(v1_1);
        return new a(v0);
    }

    private void a(a.d.d arg11) {
        int v0 = this.s;
        if(v0 < 1) {
            return;
        }
        int v0_1 = Math.min(v0, 3);
        int v1 = this.i.size();
        int v8;
        for(v8 = 0; v8 < v1; ++v8) {
            Fragment v9 = (Fragment)this.i.get(v8);
            if(v9.b < v0_1) {
                this.a(v9, v0_1, v9.s(), v9.t(), false);
                if(v9.H != null && !v9.z && (v9.N)) {
                    arg11.add(v9);
                }
            }
        }
    }

    private void a(@NonNull Fragment arg3, @NonNull a arg4, int arg5) {
        View v0 = arg3.H;
        ViewGroup v1 = arg3.G;
        v1.startViewTransition(v0);
        arg3.b(arg5);
        Animation v5 = arg4.a;
        if(v5 != null) {
            b v4 = new b(v5, v1, v0);
            arg3.a(arg3.H);
            v4.setAnimationListener(new o(this, v1, arg3));
            arg3.H.startAnimation(v4);
            return;
        }
        Animator v4_1 = arg4.b;
        arg3.a(v4_1);
        v4_1.addListener(new p(this, v1, v0, arg3));
        v4_1.setTarget(arg3.H);
        v4_1.start();
    }

    private void a(RuntimeException arg8) {
        Log.e("FragmentManager", arg8.getMessage());
        Log.e("FragmentManager", "Activity state:");
        PrintWriter v2 = new PrintWriter(new a.g.f.b("FragmentManager"));
        j v0 = this.t;
        if(v0 == null) {
            try {
                this.a("  ", null, v2, new String[0]);
            }
            catch(Exception v0_2) {
                Log.e("FragmentManager", "Failed dumping state", v0_2);
            }
        }
        else {
            try {
                v0.a("  ", null, v2, new String[0]);
            }
            catch(Exception v0_1) {
                Log.e("FragmentManager", "Failed dumping state", v0_1);
            }
        }
        throw arg8;
    }

    private void a(ArrayList arg8, ArrayList arg9) {
        int v2 = this.H == null ? 0 : this.H.size();
        int v0 = 0;
        while(v0 < v2) {
            f v3 = (f)this.H.get(v0);
            if(arg8 != null && !v3.a) {
                int v5 = arg8.indexOf(v3.b);
                if(v5 == -1 || !((Boolean)arg9.get(v5)).booleanValue()) {
                    goto label_29;
                }
                this.H.remove(v0);
                --v0;
                --v2;
                v3.c();
            }
            else {
            label_29:
                if((v3.e()) || arg8 != null && (v3.b.a(arg8, 0, arg8.size()))) {
                    this.H.remove(v0);
                    --v0;
                    --v2;
                    if(arg8 != null && !v3.a) {
                        int v5_1 = arg8.indexOf(v3.b);
                        if(v5_1 != -1 && (((Boolean)arg9.get(v5_1)).booleanValue())) {
                            v3.c();
                        }
                        else {
                            v3.d();
                        }
                    }
                    else {
                        v3.d();
                    }
                }
            }
            ++v0;
        }
    }

    private static void a(ArrayList arg3, ArrayList arg4, int arg5, int arg6) {
        while(arg5 < arg6) {
            androidx.fragment.app.a v0 = (androidx.fragment.app.a)arg3.get(arg5);
            boolean v2 = true;
            if(((Boolean)arg4.get(arg5)).booleanValue()) {
                v0.a(-1);
                if(arg5 != arg6 - 1) {
                    v2 = false;
                }
                v0.b(v2);
            }
            else {
                v0.a(1);
                v0.b();
            }
            ++arg5;
        }
    }

    private boolean a(String arg9, int arg10, int arg11) {
        this.r();
        this.c(true);
        if(this.w != null && arg10 < 0 && arg9 == null && (this.w.k().e())) {
            return 1;
        }
        boolean v9 = this.a(this.C, this.D, arg9, arg10, arg11);
        if(v9) {
            this.g = true;
            try {
                this.c(this.C, this.D);
            }
            catch(Throwable v9_1) {
                this.F();
                throw v9_1;
            }
            this.F();
        }
        this.I();
        this.q();
        this.D();
        return v9;
    }

    @Nullable
    public Fragment a(int arg4) {
        int v0;
        for(v0 = this.i.size() - 1; v0 >= 0; --v0) {
            Fragment v1 = (Fragment)this.i.get(v0);
            if(v1 != null && v1.w == arg4) {
                return v1;
            }
        }
        for(Object v1_1: this.j.values()) {
            Fragment v1_2 = (Fragment)v1_1;
            if(v1_2 == null || v1_2.w != arg4) {
                continue;
            }
            return v1_2;
        }
        return null;
    }

    @Nullable
    public Fragment a(Bundle arg5, String arg6) {
        String v5 = arg5.getString(arg6);
        if(v5 == null) {
            return null;
        }
        Fragment v1 = (Fragment)this.j.get(v5);
        if(v1 != null) {
            return v1;
        }
        this.a(new IllegalStateException("Fragment no longer exists for key " + arg6 + ": unique id " + v5));
        throw null;
    }

    @Override  // androidx.fragment.app.k
    @Nullable
    public Fragment a(@Nullable String arg4) {
        if(arg4 != null) {
            int v0;
            for(v0 = this.i.size() - 1; v0 >= 0; --v0) {
                Fragment v1 = (Fragment)this.i.get(v0);
                if(v1 != null && (arg4.equals(v1.y))) {
                    return v1;
                }
            }
        }
        if(arg4 != null) {
            for(Object v1_1: this.j.values()) {
                Fragment v1_2 = (Fragment)v1_1;
                if(v1_2 == null || !arg4.equals(v1_2.y)) {
                    continue;
                }
                return v1_2;
            }
        }
        return null;
    }

    a a(Fragment arg6, int arg7, boolean arg8, int arg9) {
        int v0 = arg6.s();
        int v1 = 0;
        arg6.a(0);
        if(arg6.G != null && arg6.G.getLayoutTransition() != null) {
            return null;
        }
        Animation v2 = arg6.a(arg7, arg8, v0);
        if(v2 != null) {
            return new a(v2);
        }
        Animator v6 = arg6.b(arg7, arg8, v0);
        if(v6 != null) {
            return new a(v6);
        }
        if(v0 != 0) {
            boolean v6_1 = "anim".equals(this.t.f().getResources().getResourceTypeName(v0));
            if(v6_1) {
                try {
                    Animation v2_1 = AnimationUtils.loadAnimation(this.t.f(), v0);
                    if(v2_1 != null) {
                        return new a(v2_1);
                    }
                    v1 = 1;
                }
                catch(Resources.NotFoundException v6_2) {
                    throw v6_2;
                    v1 = 1;
                }
                catch(RuntimeException unused_ex) {
                label_38:
                    if(v1 == 0) {
                        try {
                            Animator v1_2 = AnimatorInflater.loadAnimator(this.t.f(), v0);
                            if(v1_2 != null) {
                                return new a(v1_2);
                            }
                        }
                        catch(RuntimeException v1_1) {
                            if(v6_1) {
                                throw v1_1;
                            }
                            Animation v6_3 = AnimationUtils.loadAnimation(this.t.f(), v0);
                            if(v6_3 != null) {
                                return new a(v6_3);
                                throw v1_1;
                            }
                        }
                    }
                    goto label_56;
                }
            }
            goto label_38;
        }
    label_56:
        if(arg7 == 0) {
            return null;
        }
        int v6_4 = s.b(arg7, arg8);
        if(v6_4 < 0) {
            return null;
        }
        switch(v6_4) {
            case 1: {
                return s.a(1.125f, 1f, 0f, 1f);
            }
            case 2: {
                return s.a(1f, 0.975f, 1f, 0f);
            }
            case 3: {
                return s.a(0.975f, 1f, 0f, 1f);
            }
            case 4: {
                return s.a(1f, 1.075f, 1f, 0f);
            }
            case 5: {
                return s.a(0f, 1f);
            }
            case 6: {
                return s.a(1f, 0f);
            }
            default: {
                if(arg9 == 0 && (this.t.k())) {
                    this.t.j();
                    return null;
                }
                return null;
            }
        }
    }

    @Override  // androidx.fragment.app.k
    @NonNull
    public y a() {
        return new androidx.fragment.app.a(this);
    }

    public void a(int arg5, androidx.fragment.app.a arg6) {
        synchronized(this) {
            if(this.o == null) {
                this.o = new ArrayList();
            }
            int v0 = this.o.size();
            if(arg5 < v0) {
                if(s.c) {
                    Log.v("FragmentManager", "Setting back stack index " + arg5 + " to " + arg6);
                }
                this.o.set(arg5, arg6);
            }
            else {
                while(v0 < arg5) {
                    this.o.add(null);
                    if(this.p == null) {
                        this.p = new ArrayList();
                    }
                    if(s.c) {
                        Log.v("FragmentManager", "Adding available back stack index " + v0);
                    }
                    this.p.add(Integer.valueOf(v0));
                    ++v0;
                }
                if(s.c) {
                    Log.v("FragmentManager", "Adding back stack index " + arg5 + " with " + arg6);
                }
                this.o.add(arg6);
            }
            return;
        }
    }

    void a(int arg3, boolean arg4) {
        if(this.t == null && arg3 != 0) {
            throw new IllegalStateException("No activity");
        }
        if(!arg4 && arg3 == this.s) {
            return;
        }
        this.s = arg3;
        int v3 = this.i.size();
        int v0;
        for(v0 = 0; v0 < v3; ++v0) {
            this.l(((Fragment)this.i.get(v0)));
        }
        for(Object v0_1: this.j.values()) {
            Fragment v0_2 = (Fragment)v0_1;
            if(v0_2 == null || !v0_2.m && !v0_2.A || (v0_2.N)) {
                continue;
            }
            this.l(v0_2);
        }
        this.C();
        if(this.x) {
            j v3_2 = this.t;
            if(v3_2 != null && this.s == 4) {
                v3_2.l();
                this.x = false;
            }
        }
    }

    public void a(@NonNull Configuration arg3) {
        int v0;
        for(v0 = 0; v0 < this.i.size(); ++v0) {
            Fragment v1 = (Fragment)this.i.get(v0);
            if(v1 != null) {
                v1.a(arg3);
            }
        }
    }

    public void a(Bundle arg2, String arg3, Fragment arg4) {
        if(arg4.s == this) {
            arg2.putString(arg3, arg4.f);
            return;
        }
        this.a(new IllegalStateException("Fragment " + arg4 + " is not currently in the FragmentManager"));
        throw null;
    }

    void a(Parcelable arg12) {
        FragmentState v6_1;
        if(arg12 == null) {
            return;
        }
        FragmentManagerState v12 = (FragmentManagerState)arg12;
        if(v12.a == null) {
            return;
        }
        for(Object v1: this.I.c()) {
            Fragment v1_1 = (Fragment)v1;
            if(s.c) {
                Log.v("FragmentManager", "restoreSaveState: re-attaching retained " + v1_1);
            }
            for(Object v6: v12.a) {
                v6_1 = (FragmentState)v6;
                if(!v6_1.b.equals(v1_1.f)) {
                    continue;
                }
                goto label_38;
            }
            v6_1 = null;
        label_38:
            if(v6_1 == null) {
                if(s.c) {
                    Log.v("FragmentManager", "Discarding retained Fragment " + v1_1 + " that was not found in the set of active Fragments " + v12.a);
                }
                this.a(v1_1, 1, 0, 0, false);
                v1_1.m = true;
                this.a(v1_1, 0, 0, 0, false);
                continue;
            }
            v6_1.n = v1_1;
            v1_1.d = null;
            v1_1.r = 0;
            v1_1.o = false;
            v1_1.l = false;
            v1_1.i = v1_1.h == null ? null : v1_1.h.f;
            v1_1.h = null;
            Bundle v2 = v6_1.m;
            if(v2 == null) {
                continue;
            }
            v2.setClassLoader(this.t.f().getClassLoader());
            v1_1.d = v6_1.m.getSparseParcelableArray("android:view_state");
            v1_1.c = v6_1.m;
        }
        this.j.clear();
        for(Object v1_2: v12.a) {
            FragmentState v1_3 = (FragmentState)v1_2;
            if(v1_3 == null) {
                continue;
            }
            Fragment v5_1 = v1_3.a(this.t.f().getClassLoader(), this.c());
            v5_1.s = this;
            if(s.c) {
                Log.v("FragmentManager", "restoreSaveState: active (" + v5_1.f + "): " + v5_1);
            }
            this.j.put(v5_1.f, v5_1);
            v1_3.n = null;
        }
        this.i.clear();
        ArrayList v0_2 = v12.b;
        if(v0_2 != null) {
            for(Object v1_4: v0_2) {
                String v1_5 = (String)v1_4;
                Fragment v5_2 = (Fragment)this.j.get(v1_5);
                if(v5_2 != null) {
                    v5_2.l = true;
                    if(s.c) {
                        Log.v("FragmentManager", "restoreSaveState: added (" + v1_5 + "): " + v5_2);
                    }
                    if(!this.i.contains(v5_2)) {
                        ArrayList v1_6 = this.i;
                        synchronized(v1_6) {
                            this.i.add(v5_2);
                            continue;
                        }
                    }
                    throw new IllegalStateException("Already added " + v5_2);
                }
                this.a(new IllegalStateException("No instantiated fragment for (" + v1_5 + ")"));
                throw null;
            }
        }
        BackStackState[] v0_4 = v12.c;
        if(v0_4 == null) {
            this.k = null;
        }
        else {
            this.k = new ArrayList(v0_4.length);
            int v0_5;
            for(v0_5 = 0; true; ++v0_5) {
                BackStackState[] v1_7 = v12.c;
                if(v0_5 >= v1_7.length) {
                    break;
                }
                androidx.fragment.app.a v1_8 = v1_7[v0_5].a(this);
                if(s.c) {
                    Log.v("FragmentManager", "restoreAllState: back stack #" + v0_5 + " (index " + v1_8.u + "): " + v1_8);
                    PrintWriter v4 = new PrintWriter(new a.g.f.b("FragmentManager"));
                    v1_8.a("  ", v4, false);
                    v4.close();
                }
                this.k.add(v1_8);
                int v2_1 = v1_8.u;
                if(v2_1 >= 0) {
                    this.a(v2_1, v1_8);
                }
            }
        }
        String v0_6 = v12.d;
        if(v0_6 != null) {
            this.w = (Fragment)this.j.get(v0_6);
            this.u(this.w);
        }
        this.h = v12.e;
    }

    public void a(@NonNull Menu arg3) {
        if(this.s < 1) {
            return;
        }
        int v0;
        for(v0 = 0; v0 < this.i.size(); ++v0) {
            Fragment v1 = (Fragment)this.i.get(v0);
            if(v1 != null) {
                v1.c(arg3);
            }
        }
    }

    void a(@NonNull Fragment arg4) {
        if(this.x()) {
            if(s.c) {
                Log.v("FragmentManager", "Ignoring addRetainedFragment as the state is already saved");
            }
            return;
        }
        if((this.I.a(arg4)) && (s.c)) {
            Log.v("FragmentManager", "Updating retained Fragments: Added " + arg4);
        }
    }

    void a(Fragment arg19, int arg20, int arg21, int arg22, boolean arg23) {
        int v0_17;
        int v0_16;
        int v0_4;
        int v0_3;
        String v0_15;
        ViewGroup v0_14;
        String v10;
        String v9;
        int v0;
        s v6 = this;
        Fragment v7 = arg19;
        boolean v8 = true;
        if((v7.l) && !v7.A) {
            v0 = arg20;
        }
        else {
            v0 = arg20;
            if(v0 > 1) {
                v0 = 1;
            }
        }
        if(v7.m) {
            int v1 = v7.b;
            if(v0 > v1) {
                v0 = v1 != 0 || !arg19.I() ? v7.b : 1;
            }
        }
        if((v7.J) && v7.b < 3 && v0 > 2) {
            v0 = 2;
        }
        int v11 = v7.S == androidx.lifecycle.f.b.c ? Math.min(v0, 1) : Math.min(v0, v7.S.ordinal());
        int v0_1 = v7.b;
        if(v0_1 <= v11) {
            if((v7.n) && !v7.o) {
                return;
            }
            if(arg19.i() != null || arg19.j() != null) {
                v7.a(null);
                v7.a(null);
                this.a(arg19, arg19.C(), 0, 0, true);
            }
            int v0_2 = v7.b;
            if(v0_2 == 0) {
                if(v11 > 0) {
                    if(s.c) {
                        Log.v("FragmentManager", "moveto CREATED: " + v7);
                    }
                    Bundle v0_5 = v7.c;
                    if(v0_5 != null) {
                        v0_5.setClassLoader(v6.t.f().getClassLoader());
                        v7.d = v7.c.getSparseParcelableArray("android:view_state");
                        Fragment v0_6 = v6.a(v7.c, "android:target_state");
                        v7.i = v0_6 == null ? null : v0_6.f;
                        if(v7.i != null) {
                            v7.j = v7.c.getInt("android:target_req_state", 0);
                        }
                        Boolean v0_7 = v7.e;
                        if(v0_7 == null) {
                            v7.K = v7.c.getBoolean("android:user_visible_hint", true);
                        }
                        else {
                            v7.K = v0_7.booleanValue();
                            v7.e = null;
                        }
                        if(!v7.K) {
                            v7.J = true;
                            if(v11 > 2) {
                                v11 = 2;
                            }
                        }
                    }
                    j v0_8 = v6.t;
                    v7.t = v0_8;
                    Fragment v1_1 = v6.v;
                    v7.v = v1_1;
                    v7.s = v1_1 == null ? v0_8.e : v1_1.u;
                    Fragment v0_9 = v7.h;
                    if(v0_9 == null) {
                        v9 = "Fragment ";
                        v10 = " declared target fragment ";
                    }
                    else {
                        Object v0_10 = v6.j.get(v0_9.f);
                        Fragment v1_2 = v7.h;
                        if(v0_10 == v1_2) {
                            if(v1_2.b < 1) {
                                v9 = "Fragment ";
                                v10 = " declared target fragment ";
                                this.a(v1_2, 1, 0, 0, true);
                            }
                            else {
                                v9 = "Fragment ";
                                v10 = " declared target fragment ";
                            }
                            v7.i = v7.h.f;
                            v7.h = null;
                            goto label_178;
                        }
                        throw new IllegalStateException("Fragment " + v7 + " declared target fragment " + v7.h + " that does not belong to this FragmentManager!");
                    }
                label_178:
                    String v0_11 = v7.i;
                    if(v0_11 != null) {
                        Fragment v1_3 = (Fragment)v6.j.get(v0_11);
                        if(v1_3 == null) {
                            throw new IllegalStateException(v9 + v7 + v10 + v7.i + " that does not belong to this FragmentManager!");
                        }
                        if(v1_3.b < 1) {
                            this.a(v1_3, 1, 0, 0, true);
                            goto label_206;
                            throw new IllegalStateException(v9 + v7 + v10 + v7.i + " that does not belong to this FragmentManager!");
                        }
                    }
                label_206:
                    v6.b(v7, v6.t.f(), false);
                    arg19.U();
                    Fragment v0_12 = v7.v;
                    if(v0_12 == null) {
                        v6.t.a(v7);
                    }
                    else {
                        v0_12.a(v7);
                    }
                    v6.a(v7, v6.t.f(), false);
                    if(v7.R) {
                        v7.k(v7.c);
                        v7.b = 1;
                    }
                    else {
                        v6.c(v7, v7.c, false);
                        v7.h(v7.c);
                        v6.b(v7, v7.c, false);
                    }
                }
            label_231:
                if(v11 > 0) {
                    this.e(arg19);
                }
                if(v11 > 1) {
                    if(s.c) {
                        Log.v("FragmentManager", "moveto ACTIVITY_CREATED: " + v7);
                    }
                    if(!v7.n) {
                        int v0_13 = v7.x;
                        if(v0_13 == 0) {
                            v0_14 = null;
                        }
                        else {
                            if(v0_13 == -1) {
                                v6.a(new IllegalArgumentException("Cannot create fragment " + v7 + " for a container view with no id"));
                                throw null;
                            }
                            v0_14 = (ViewGroup)v6.u.a(v0_13);
                            if(v0_14 == null && !v7.p) {
                                try {
                                    v0_15 = arg19.x().getResourceName(v7.x);
                                }
                                catch(Resources.NotFoundException unused_ex) {
                                    v0_15 = "unknown";
                                }
                                v6.a(new IllegalArgumentException("No view found for id 0x" + Integer.toHexString(v7.x) + " (" + v0_15 + ") for fragment " + v7));
                                throw null;
                                v6.a(new IllegalArgumentException("Cannot create fragment " + v7 + " for a container view with no id"));
                                throw null;
                            }
                        }
                        v7.G = v0_14;
                        v7.b(v7.i(v7.c), v0_14, v7.c);
                        View v1_4 = v7.H;
                        if(v1_4 == null) {
                            v7.I = null;
                        }
                        else {
                            v7.I = v1_4;
                            v1_4.setSaveFromParentEnabled(false);
                            if(v0_14 != null) {
                                v0_14.addView(v7.H);
                            }
                            if(v7.z) {
                                v7.H.setVisibility(8);
                            }
                            v7.a(v7.H, v7.c);
                            v6.a(v7, v7.H, v7.c, false);
                            if(v7.H.getVisibility() != 0 || v7.G == null) {
                                v8 = false;
                            }
                            v7.N = v8;
                        }
                    }
                    v7.g(v7.c);
                    v6.a(v7, v7.c, false);
                    if(v7.H != null) {
                        v7.l(v7.c);
                    }
                    v7.c = null;
                }
                v0_4 = 2;
            label_337:
                if(v11 > v0_4) {
                    if(s.c) {
                        Log.v("FragmentManager", "moveto STARTED: " + v7);
                    }
                    arg19.ca();
                    v6.f(v7, false);
                }
                v0_3 = 3;
            label_350:
                if(v11 > v0_3) {
                    if(s.c) {
                        Log.v("FragmentManager", "moveto RESUMED: " + v7);
                    }
                    arg19.ba();
                    v6.e(v7, false);
                    v7.c = null;
                    v7.d = null;
                }
                v8 = v11;
            }
            else {
                if(v0_2 == 1) {
                    goto label_231;
                }
                if(v0_2 != 2) {
                    if(v0_2 == 3) {
                        v0_3 = 3;
                        goto label_350;
                    }
                    v8 = v11;
                    goto label_571;
                }
                v0_4 = 2;
                goto label_337;
            }
        }
        else if(v0_1 <= v11) {
            v8 = v11;
        }
        else if(v0_1 == 1) {
        label_470:
            if(v11 < 1) {
                if(v6.A) {
                    if(arg19.i() != null) {
                        View v0_20 = arg19.i();
                        v7.a(null);
                        v0_20.clearAnimation();
                    }
                    else if(arg19.j() != null) {
                        Animator v0_21 = arg19.j();
                        v7.a(null);
                        v0_21.cancel();
                    }
                }
                if(arg19.i() == null && arg19.j() == null) {
                    if(s.c) {
                        Log.v("FragmentManager", "movefrom CREATED: " + v7);
                    }
                    int v0_22 = !v7.m || (arg19.I()) ? 0 : 1;
                    if(v0_22 == 0 && !v6.I.f(v7)) {
                        v7.b = 0;
                    }
                    else {
                        j v1_6 = v6.t;
                        if((v1_6 instanceof u)) {
                            v8 = v6.I.d();
                        }
                        else if((v1_6.f() instanceof Activity)) {
                            v8 = 1 ^ ((Activity)v6.t.f()).isChangingConfigurations();
                        }
                        if(v0_22 != 0 || (v8)) {
                            v6.I.b(v7);
                        }
                        arg19.V();
                        v6.b(v7, false);
                    }
                    arg19.X();
                    v6.c(v7, false);
                    if(arg23) {
                    }
                    else if(v0_22 == 0 && !v6.I.f(v7)) {
                        v7.t = null;
                        v7.v = null;
                        v7.s = null;
                        String v0_23 = v7.i;
                        if(v0_23 != null) {
                            Fragment v0_24 = (Fragment)v6.j.get(v0_23);
                            if(v0_24 != null && (v0_24.y())) {
                                v7.h = v0_24;
                            }
                        }
                    }
                    else {
                        this.k(arg19);
                    }
                    v8 = v11;
                }
                else {
                    v7.b(v11);
                }
            }
            else {
                v8 = v11;
            }
        }
        else {
            if(v0_1 == 2) {
                v0_17 = 2;
            }
            else {
                if(v0_1 != 3) {
                    if(v0_1 == 4) {
                        if(v11 < 4) {
                            if(s.c) {
                                Log.v("FragmentManager", "movefrom RESUMED: " + v7);
                            }
                            arg19.Z();
                            v6.d(v7, false);
                        }
                        v0_16 = 3;
                        goto label_395;
                    }
                    v8 = v11;
                    goto label_571;
                }
                v0_16 = 3;
            label_395:
                if(v11 < v0_16) {
                    if(s.c) {
                        Log.v("FragmentManager", "movefrom STARTED: " + v7);
                    }
                    arg19.da();
                    v6.g(v7, false);
                }
                v0_17 = 2;
            }
            if(v11 < v0_17) {
                if(s.c) {
                    Log.v("FragmentManager", "movefrom ACTIVITY_CREATED: " + v7);
                }
                if(v7.H != null && (v6.t.b(v7)) && v7.d == null) {
                    this.r(arg19);
                }
                arg19.W();
                v6.h(v7, false);
                View v0_18 = v7.H;
                if(v0_18 != null) {
                    ViewGroup v1_5 = v7.G;
                    if(v1_5 != null) {
                        v1_5.endViewTransition(v0_18);
                        v7.H.clearAnimation();
                        if(arg19.v() == null || !arg19.v().m) {
                            a v0_19 = v6.s <= 0 || (v6.A) || v7.H.getVisibility() != 0 || v7.P < 0f ? null : v6.a(v7, arg21, false, arg22);
                            v7.P = 0f;
                            if(v0_19 != null) {
                                v6.a(v7, v0_19, v11);
                            }
                            v7.G.removeView(v7.H);
                        }
                    }
                }
                v7.G = null;
                v7.H = null;
                v7.U = null;
                v7.V.a(null);
                v7.I = null;
                v7.o = false;
            }
            goto label_470;
        }
    label_571:
        if(v7.b != ((int)v8)) {
            Log.w("FragmentManager", "moveToState: Fragment state for " + v7 + " not updated inline; expected state " + ((int)v8) + " found " + v7.b);
            v7.b = (int)v8;
        }
    }

    void a(@NonNull Fragment arg4, @NonNull Context arg5, boolean arg6) {
        Fragment v0 = this.v;
        if(v0 != null) {
            k v0_1 = v0.q();
            if((v0_1 instanceof s)) {
                ((s)v0_1).a(arg4, arg5, true);
            }
        }
        for(Object v1: this.r) {
            c v1_1 = (c)v1;
            if((arg6) && !v1_1.b) {
                continue;
            }
            v1_1.a.a(this, arg4, arg5);
        }
    }

    void a(@NonNull Fragment arg4, @Nullable Bundle arg5, boolean arg6) {
        Fragment v0 = this.v;
        if(v0 != null) {
            k v0_1 = v0.q();
            if((v0_1 instanceof s)) {
                ((s)v0_1).a(arg4, arg5, true);
            }
        }
        for(Object v1: this.r) {
            c v1_1 = (c)v1;
            if((arg6) && !v1_1.b) {
                continue;
            }
            v1_1.a.a(this, arg4, arg5);
        }
    }

    void a(@NonNull Fragment arg4, @NonNull View arg5, @Nullable Bundle arg6, boolean arg7) {
        Fragment v0 = this.v;
        if(v0 != null) {
            k v0_1 = v0.q();
            if((v0_1 instanceof s)) {
                ((s)v0_1).a(arg4, arg5, arg6, true);
            }
        }
        for(Object v1: this.r) {
            c v1_1 = (c)v1;
            if((arg7) && !v1_1.b) {
                continue;
            }
            v1_1.a.a(this, arg4, arg5, arg6);
        }
    }

    public void a(Fragment arg3, androidx.lifecycle.f.b arg4) {
        if(this.j.get(arg3.f) == arg3 && (arg3.t == null || arg3.q() == this)) {
            arg3.S = arg4;
            return;
        }
        throw new IllegalArgumentException("Fragment " + arg3 + " is not an active fragment of FragmentManager " + this);
    }

    public void a(Fragment arg4, boolean arg5) {
        if(s.c) {
            Log.v("FragmentManager", "add: " + arg4);
        }
        this.j(arg4);
        if(!arg4.A) {
            if(this.i.contains(arg4)) {
                throw new IllegalStateException("Fragment already added: " + arg4);
            }
            ArrayList v0 = this.i;
            synchronized(v0) {
                this.i.add(arg4);
            }
            arg4.l = true;
            arg4.m = false;
            if(arg4.H == null) {
                arg4.O = false;
            }
            if(this.w(arg4)) {
                this.x = true;
            }
            if(arg5) {
                this.m(arg4);
                return;
                throw new IllegalStateException("Fragment already added: " + arg4);
            }
        }
    }

    void a(androidx.fragment.app.a arg2) {
        if(this.k == null) {
            this.k = new ArrayList();
        }
        this.k.add(arg2);
    }

    void a(androidx.fragment.app.a arg8, boolean arg9, boolean arg10, boolean arg11) {
        if(arg9) {
            arg8.b(arg11);
        }
        else {
            arg8.b();
        }
        ArrayList v1 = new ArrayList(1);
        ArrayList v2 = new ArrayList(1);
        v1.add(arg8);
        v2.add(Boolean.valueOf(arg9));
        if(arg10) {
            D.a(this, v1, v2, 0, 1, true);
        }
        if(arg11) {
            this.a(this.s, true);
        }
        for(Object v10: this.j.values()) {
            Fragment v10_1 = (Fragment)v10;
            if(v10_1 == null || v10_1.H == null || !v10_1.N || !arg8.b(v10_1.x)) {
                continue;
            }
            float v0 = v10_1.P;
            if(v0 > 0f) {
                v10_1.H.setAlpha(v0);
            }
            if(arg11) {
                v10_1.P = 0f;
                continue;
            }
            v10_1.P = -1f;
            v10_1.N = false;
        }
    }

    public void a(@NonNull j arg3, @NonNull g arg4, @Nullable Fragment arg5) {
        if(this.t == null) {
            this.t = arg3;
            this.u = arg4;
            this.v = arg5;
            if(this.v != null) {
                this.I();
            }
            if((arg3 instanceof androidx.activity.d)) {
                androidx.activity.d v4 = (androidx.activity.d)arg3;
                this.m = v4.b();
                if(arg5 != null) {
                    v4 = arg5;
                }
                this.m.a(v4, this.n);
            }
            if(arg5 != null) {
                this.I = arg5.s.f(arg5);
                return;
            }
            if((arg3 instanceof u)) {
                this.I = v.a(((u)arg3).d());
                return;
            }
            this.I = new v(false);
            return;
        }
        throw new IllegalStateException("Already attached");
    }

    public void a(e arg2, boolean arg3) {
        if(!arg3) {
            this.E();
        }
        __monitor_enter(this);
        try {
            if(!this.A && this.t != null) {
                if(this.f == null) {
                    this.f = new ArrayList();
                }
                this.f.add(arg2);
                this.B();
                __monitor_exit(this);
                return;
            }
            if(arg3) {
                __monitor_exit(this);
                return;
            }
            throw new IllegalStateException("Activity has been destroyed");
        label_26:
            __monitor_exit(this);
        }
        catch(Throwable v2) {
            goto label_26;
        }
        throw v2;
    }

    @Override  // androidx.fragment.app.k
    public void a(@NonNull String arg5, @Nullable FileDescriptor arg6, @NonNull PrintWriter arg7, @Nullable String[] arg8) {
        String v0 = arg5 + "    ";
        if(!this.j.isEmpty()) {
            arg7.print(arg5);
            arg7.print("Active Fragments in ");
            arg7.print(Integer.toHexString(System.identityHashCode(this)));
            arg7.println(":");
            for(Object v2: this.j.values()) {
                Fragment v2_1 = (Fragment)v2;
                arg7.print(arg5);
                arg7.println(v2_1);
                if(v2_1 == null) {
                    continue;
                }
                v2_1.a(v0, arg6, arg7, arg8);
            }
        }
        int v6 = this.i.size();
        int v8 = 0;
        if(v6 > 0) {
            arg7.print(arg5);
            arg7.println("Added Fragments:");
            int v1_1;
            for(v1_1 = 0; v1_1 < v6; ++v1_1) {
                Fragment v2_2 = (Fragment)this.i.get(v1_1);
                arg7.print(arg5);
                arg7.print("  #");
                arg7.print(v1_1);
                arg7.print(": ");
                arg7.println(v2_2.toString());
            }
        }
        ArrayList v6_1 = this.l;
        if(v6_1 != null) {
            int v6_2 = v6_1.size();
            if(v6_2 > 0) {
                arg7.print(arg5);
                arg7.println("Fragments Created Menus:");
                int v1_2;
                for(v1_2 = 0; v1_2 < v6_2; ++v1_2) {
                    Fragment v2_3 = (Fragment)this.l.get(v1_2);
                    arg7.print(arg5);
                    arg7.print("  #");
                    arg7.print(v1_2);
                    arg7.print(": ");
                    arg7.println(v2_3.toString());
                }
            }
        }
        ArrayList v6_3 = this.k;
        if(v6_3 != null) {
            int v6_4 = v6_3.size();
            if(v6_4 > 0) {
                arg7.print(arg5);
                arg7.println("Back Stack:");
                int v1_3;
                for(v1_3 = 0; v1_3 < v6_4; ++v1_3) {
                    androidx.fragment.app.a v2_4 = (androidx.fragment.app.a)this.k.get(v1_3);
                    arg7.print(arg5);
                    arg7.print("  #");
                    arg7.print(v1_3);
                    arg7.print(": ");
                    arg7.println(v2_4.toString());
                    v2_4.a(v0, arg7);
                }
            }
        }
        synchronized(this) {
            if(this.o != null) {
                int v6_5 = this.o.size();
                if(v6_5 > 0) {
                    arg7.print(arg5);
                    arg7.println("Back Stack Indices:");
                    int v0_1;
                    for(v0_1 = 0; v0_1 < v6_5; ++v0_1) {
                        androidx.fragment.app.a v1_4 = (androidx.fragment.app.a)this.o.get(v0_1);
                        arg7.print(arg5);
                        arg7.print("  #");
                        arg7.print(v0_1);
                        arg7.print(": ");
                        arg7.println(v1_4);
                    }
                }
            }
            if(this.p != null && this.p.size() > 0) {
                arg7.print(arg5);
                arg7.print("mAvailBackStackIndices: ");
                arg7.println(Arrays.toString(this.p.toArray()));
            }
        }
        ArrayList v6_6 = this.f;
        if(v6_6 != null) {
            int v6_7 = v6_6.size();
            if(v6_7 > 0) {
                arg7.print(arg5);
                arg7.println("Pending Actions:");
                while(v8 < v6_7) {
                    e v0_2 = (e)this.f.get(v8);
                    arg7.print(arg5);
                    arg7.print("  #");
                    arg7.print(v8);
                    arg7.print(": ");
                    arg7.println(v0_2);
                    ++v8;
                }
            }
        }
        arg7.print(arg5);
        arg7.println("FragmentManager misc state:");
        arg7.print(arg5);
        arg7.print("  mHost=");
        arg7.println(this.t);
        arg7.print(arg5);
        arg7.print("  mContainer=");
        arg7.println(this.u);
        if(this.v != null) {
            arg7.print(arg5);
            arg7.print("  mParent=");
            arg7.println(this.v);
        }
        arg7.print(arg5);
        arg7.print("  mCurState=");
        arg7.print(this.s);
        arg7.print(" mStateSaved=");
        arg7.print(this.y);
        arg7.print(" mStopped=");
        arg7.print(this.z);
        arg7.print(" mDestroyed=");
        arg7.println(this.A);
        if(this.x) {
            arg7.print(arg5);
            arg7.print("  mNeedMenuInvalidate=");
            arg7.println(this.x);
        }
    }

    public void a(boolean arg3) {
        int v0;
        for(v0 = this.i.size() - 1; v0 >= 0; --v0) {
            Fragment v1 = (Fragment)this.i.get(v0);
            if(v1 != null) {
                v1.e(arg3);
            }
        }
    }

    public boolean a(@NonNull Menu arg8, @NonNull MenuInflater arg9) {
        int v1 = 0;
        if(this.s < 1) {
            return 0;
        }
        ArrayList v3 = null;
        int v0 = 0;
        boolean v4 = false;
        while(v0 < this.i.size()) {
            Fragment v5 = (Fragment)this.i.get(v0);
            if(v5 != null && (v5.b(arg8, arg9))) {
                if(v3 == null) {
                    v3 = new ArrayList();
                }
                v3.add(v5);
                v4 = true;
            }
            ++v0;
        }
        if(this.l != null) {
            while(v1 < this.l.size()) {
                Fragment v8 = (Fragment)this.l.get(v1);
                if(v3 == null || !v3.contains(v8)) {
                    v8.N();
                }
                ++v1;
            }
        }
        this.l = v3;
        return v4;
    }

    public boolean a(@NonNull MenuItem arg5) {
        if(this.s < 1) {
            return 0;
        }
        int v0;
        for(v0 = 0; v0 < this.i.size(); ++v0) {
            Fragment v3 = (Fragment)this.i.get(v0);
            if(v3 != null && (v3.c(arg5))) {
                return 1;
            }
        }
        return 0;
    }

    boolean a(ArrayList arg6, ArrayList arg7, String arg8, int arg9, int arg10) {
        int v0_1;
        ArrayList v0 = this.k;
        if(v0 == null) {
            return 0;
        }
        if(arg8 == null && arg9 < 0 && (arg10 & 1) == 0) {
            int v8 = v0.size() - 1;
            if(v8 < 0) {
                return 0;
            }
            arg6.add(this.k.remove(v8));
            arg7.add(Boolean.valueOf(true));
            return 1;
        }
        if(arg8 == null && arg9 < 0) {
            v0_1 = -1;
        }
        else {
            for(v0_1 = this.k.size() - 1; v0_1 >= 0; --v0_1) {
                androidx.fragment.app.a v3 = (androidx.fragment.app.a)this.k.get(v0_1);
                if(arg8 != null && (arg8.equals(v3.c())) || arg9 >= 0 && arg9 == v3.u) {
                    break;
                }
            }
            if(v0_1 < 0) {
                return 0;
            }
            if((arg10 & 1) != 0) {
                do {
                label_47:
                    --v0_1;
                    if(v0_1 < 0) {
                        break;
                    }
                    androidx.fragment.app.a v10 = (androidx.fragment.app.a)this.k.get(v0_1);
                    if(arg8 != null && (arg8.equals(v10.c())) || arg9 >= 0 && arg9 == v10.u) {
                        goto label_47;
                    }
                    break;
                }
                while(true);
            }
        }
        if(v0_1 == this.k.size() - 1) {
            return 0;
        }
        int v8_1;
        for(v8_1 = this.k.size() - 1; v8_1 > v0_1; --v8_1) {
            arg6.add(this.k.remove(v8_1));
            arg7.add(Boolean.valueOf(true));
        }
        return 1;
    }

    public static int b(int arg1, boolean arg2) {
        if(arg1 != 0x1001) {
            if(arg1 != 0x1003) {
                if(arg1 != 0x2002) {
                    return -1;
                }
                return arg2 ? 3 : 4;
            }
            return arg2 ? 5 : 6;
        }
        return arg2 ? 1 : 2;
    }

    private void b(a.d.d arg6) {
        int v0 = arg6.size();
        int v1;
        for(v1 = 0; v1 < v0; ++v1) {
            Fragment v2 = (Fragment)arg6.c(v1);
            if(!v2.l) {
                View v3 = v2.ga();
                v2.P = v3.getAlpha();
                v3.setAlpha(0f);
            }
        }
    }

    private void b(ArrayList arg16, ArrayList arg17, int arg18, int arg19) {
        int v4;
        int v9 = arg18;
        boolean v11 = ((androidx.fragment.app.a)arg16.get(v9)).q;
        ArrayList v0 = this.E;
        if(v0 == null) {
            this.E = new ArrayList();
        }
        else {
            v0.clear();
        }
        this.E.addAll(this.i);
        Fragment v2 = this.u();
        int v12 = 0;
        int v0_1;
        for(v0_1 = v9; v0_1 < arg19; ++v0_1) {
            androidx.fragment.app.a v3 = (androidx.fragment.app.a)arg16.get(v0_1);
            v2 = ((Boolean)arg17.get(v0_1)).booleanValue() ? v3.b(this.E, v2) : v3.a(this.E, v2);
            v12 = v12 != 0 || (v3.h) ? 1 : 0;
        }
        this.E.clear();
        if(!v11) {
            D.a(this, arg16, arg17, arg18, arg19, false);
        }
        s.a(arg16, arg17, arg18, arg19);
        if(v11) {
            a.d.d v14 = new a.d.d();
            this.a(v14);
            int v0_2 = this.a(arg16, arg17, arg18, arg19, v14);
            this.b(v14);
            v4 = v0_2;
        }
        else {
            v4 = arg19;
        }
        if(v4 != v9 && (v11)) {
            D.a(this, arg16, arg17, arg18, v4, true);
            this.a(this.s, true);
        }
        while(v9 < arg19) {
            androidx.fragment.app.a v0_3 = (androidx.fragment.app.a)arg16.get(v9);
            if(((Boolean)arg17.get(v9)).booleanValue()) {
                int v1 = v0_3.u;
                if(v1 >= 0) {
                    this.b(v1);
                    v0_3.u = -1;
                }
            }
            v0_3.e();
            ++v9;
        }
        if(v12 != 0) {
            this.z();
        }
    }

    private boolean b(ArrayList arg5, ArrayList arg6) {
        synchronized(this) {
            int v1 = 0;
            if(this.f != null && this.f.size() != 0) {
                int v0 = this.f.size();
                int v2 = 0;
                while(v1 < v0) {
                    v2 |= ((e)this.f.get(v1)).a(arg5, arg6);
                    ++v1;
                }
                this.f.clear();
                this.t.g().removeCallbacks(this.J);
                return (boolean)v2;
            }
            return 0;
        }
    }

    public int b(androidx.fragment.app.a arg5) {
        synchronized(this) {
            if(this.p != null && this.p.size() > 0) {
                if(s.c) {
                    Log.v("FragmentManager", "Adding back stack index " + ((int)(((Integer)this.p.remove(this.p.size() - 1)))) + " with " + arg5);
                }
                this.o.set(((Integer)this.p.remove(this.p.size() - 1)).intValue(), arg5);
                return (int)(((Integer)this.p.remove(this.p.size() - 1)));
            }
            if(this.o == null) {
                this.o = new ArrayList();
            }
            int v0 = this.o.size();
            if(s.c) {
                Log.v("FragmentManager", "Setting back stack index " + v0 + " to " + arg5);
            }
            this.o.add(arg5);
            return v0;
        }
    }

    public Fragment b(@NonNull String arg3) {
        for(Object v1: this.j.values()) {
            Fragment v1_1 = (Fragment)v1;
            if(v1_1 == null) {
                continue;
            }
            Fragment v1_2 = v1_1.a(arg3);
            if(v1_2 == null) {
                continue;
            }
            return v1_2;
        }
        return null;
    }

    public void b(int arg4) {
        synchronized(this) {
            this.o.set(arg4, null);
            if(this.p == null) {
                this.p = new ArrayList();
            }
            if(s.c) {
                Log.v("FragmentManager", "Freeing back stack index " + arg4);
            }
            this.p.add(Integer.valueOf(arg4));
            return;
        }
    }

    public void b(Fragment arg4) {
        if(s.c) {
            Log.v("FragmentManager", "attach: " + arg4);
        }
        if(arg4.A) {
            arg4.A = false;
            if(!arg4.l) {
                if(this.i.contains(arg4)) {
                    throw new IllegalStateException("Fragment already added: " + arg4);
                }
                if(s.c) {
                    Log.v("FragmentManager", "add from attach: " + arg4);
                }
                ArrayList v0 = this.i;
                synchronized(v0) {
                    this.i.add(arg4);
                }
                arg4.l = true;
                if(this.w(arg4)) {
                    this.x = true;
                    return;
                    throw new IllegalStateException("Fragment already added: " + arg4);
                }
            }
        }
    }

    void b(@NonNull Fragment arg4, @NonNull Context arg5, boolean arg6) {
        Fragment v0 = this.v;
        if(v0 != null) {
            k v0_1 = v0.q();
            if((v0_1 instanceof s)) {
                ((s)v0_1).b(arg4, arg5, true);
            }
        }
        for(Object v1: this.r) {
            c v1_1 = (c)v1;
            if((arg6) && !v1_1.b) {
                continue;
            }
            v1_1.a.b(this, arg4, arg5);
        }
    }

    void b(@NonNull Fragment arg4, @Nullable Bundle arg5, boolean arg6) {
        Fragment v0 = this.v;
        if(v0 != null) {
            k v0_1 = v0.q();
            if((v0_1 instanceof s)) {
                ((s)v0_1).b(arg4, arg5, true);
            }
        }
        for(Object v1: this.r) {
            c v1_1 = (c)v1;
            if((arg6) && !v1_1.b) {
                continue;
            }
            v1_1.a.b(this, arg4, arg5);
        }
    }

    void b(@NonNull Fragment arg4, boolean arg5) {
        Fragment v0 = this.v;
        if(v0 != null) {
            k v0_1 = v0.q();
            if((v0_1 instanceof s)) {
                ((s)v0_1).b(arg4, true);
            }
        }
        for(Object v1: this.r) {
            c v1_1 = (c)v1;
            if((arg5) && !v1_1.b) {
                continue;
            }
            v1_1.a.a(this, arg4);
        }
    }

    public void b(boolean arg3) {
        int v0;
        for(v0 = this.i.size() - 1; v0 >= 0; --v0) {
            Fragment v1 = (Fragment)this.i.get(v0);
            if(v1 != null) {
                v1.f(arg3);
            }
        }
    }

    @Override  // androidx.fragment.app.k
    public boolean b() {
        boolean v0 = this.r();
        this.H();
        return v0;
    }

    public boolean b(@NonNull Menu arg5) {
        int v1 = 0;
        if(this.s < 1) {
            return 0;
        }
        boolean v0 = false;
        while(v1 < this.i.size()) {
            Fragment v3 = (Fragment)this.i.get(v1);
            if(v3 != null && (v3.d(arg5))) {
                v0 = true;
            }
            ++v1;
        }
        return v0;
    }

    public boolean b(@NonNull MenuItem arg5) {
        if(this.s < 1) {
            return 0;
        }
        int v0;
        for(v0 = 0; v0 < this.i.size(); ++v0) {
            Fragment v3 = (Fragment)this.i.get(v0);
            if(v3 != null && (v3.d(arg5))) {
                return 1;
            }
        }
        return 0;
    }

    private void c(ArrayList arg5, ArrayList arg6) {
        if(arg5 != null && !arg5.isEmpty()) {
            if(arg6 != null && arg5.size() == arg6.size()) {
                this.a(arg5, arg6);
                int v0 = arg5.size();
                int v1 = 0;
                int v2 = 0;
                while(v1 < v0) {
                    if(!((androidx.fragment.app.a)arg5.get(v1)).q) {
                        if(v2 != v1) {
                            this.b(arg5, arg6, v2, v1);
                        }
                        v2 = v1 + 1;
                        if(((Boolean)arg6.get(v1)).booleanValue()) {
                            while(v2 < v0 && (((Boolean)arg6.get(v2)).booleanValue()) && !((androidx.fragment.app.a)arg5.get(v2)).q) {
                                ++v2;
                            }
                        }
                        this.b(arg5, arg6, v1, v2);
                        v1 = v2 - 1;
                    }
                    ++v1;
                }
                if(v2 != v0) {
                    this.b(arg5, arg6, v2, v0);
                }
                return;
            }
            throw new IllegalStateException("Internal error with the back stack records");
        }
    }

    private void c(boolean arg3) {
        if(!this.g) {
            if(this.t != null) {
                if(Looper.myLooper() == this.t.g().getLooper()) {
                    if(!arg3) {
                        this.E();
                    }
                    if(this.C == null) {
                        this.C = new ArrayList();
                        this.D = new ArrayList();
                    }
                    this.g = true;
                    try {
                        this.a(null, null);
                    }
                    catch(Throwable v0) {
                        this.g = false;
                        throw v0;
                    }
                    this.g = false;
                    return;
                }
                throw new IllegalStateException("Must be called from main thread of fragment host");
            }
            throw new IllegalStateException("Fragment host has been destroyed");
        }
        throw new IllegalStateException("FragmentManager is already executing transactions");
    }

    @Override  // androidx.fragment.app.k
    @NonNull
    public i c() {
        if(super.c() == k.a) {
            Fragment v0 = this.v;
            if(v0 != null) {
                return v0.s.c();
            }
            this.a(new r(this));
        }
        return super.c();
    }

    void c(Fragment arg8) {
        if(arg8.H != null) {
            a v0 = this.a(arg8, arg8.t(), arg8.z ^ 1, arg8.u());
            if(v0 == null) {
            label_33:
                if(v0 != null) {
                    arg8.H.startAnimation(v0.a);
                    v0.a.start();
                }
                arg8.H.setVisibility(!arg8.z || (arg8.H()) ? 0 : 8);
                if(arg8.H()) {
                    arg8.g(false);
                }
            }
            else {
                Animator v3 = v0.b;
                if(v3 == null) {
                    goto label_33;
                }
                v3.setTarget(arg8.H);
                if(!arg8.z) {
                    arg8.H.setVisibility(0);
                }
                else if(arg8.H()) {
                    arg8.g(false);
                }
                else {
                    ViewGroup v3_1 = arg8.G;
                    View v4 = arg8.H;
                    v3_1.startViewTransition(v4);
                    v0.b.addListener(new q(this, v3_1, v4, arg8));
                }
                v0.b.start();
            }
        }
        if((arg8.l) && (this.w(arg8))) {
            this.x = true;
        }
        arg8.O = false;
        arg8.a(arg8.z);
    }

    void c(@NonNull Fragment arg4, @Nullable Bundle arg5, boolean arg6) {
        Fragment v0 = this.v;
        if(v0 != null) {
            k v0_1 = v0.q();
            if((v0_1 instanceof s)) {
                ((s)v0_1).c(arg4, arg5, true);
            }
        }
        for(Object v1: this.r) {
            c v1_1 = (c)v1;
            if((arg6) && !v1_1.b) {
                continue;
            }
            v1_1.a.c(this, arg4, arg5);
        }
    }

    void c(@NonNull Fragment arg4, boolean arg5) {
        Fragment v0 = this.v;
        if(v0 != null) {
            k v0_1 = v0.q();
            if((v0_1 instanceof s)) {
                ((s)v0_1).c(arg4, true);
            }
        }
        for(Object v1: this.r) {
            c v1_1 = (c)v1;
            if((arg5) && !v1_1.b) {
                continue;
            }
            v1_1.a.b(this, arg4);
        }
    }

    boolean c(int arg2) {
        return this.s >= arg2;
    }

    public static int d(int arg3) {
        if(arg3 != 0x1001) {
            if(arg3 != 0x1003) {
                return arg3 == 0x2002 ? 0x1001 : 0;
            }
            return 0x1003;
        }
        return 0x2002;
    }

    @Override  // androidx.fragment.app.k
    public List d() {
        if(this.i.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList v0 = this.i;
        synchronized(v0) {
            return (List)this.i.clone();
        }
    }

    public void d(Fragment arg4) {
        if(s.c) {
            Log.v("FragmentManager", "detach: " + arg4);
        }
        if(!arg4.A) {
            arg4.A = true;
            if(arg4.l) {
                if(s.c) {
                    Log.v("FragmentManager", "remove from detach: " + arg4);
                }
                ArrayList v1 = this.i;
                synchronized(v1) {
                    this.i.remove(arg4);
                }
                if(this.w(arg4)) {
                    this.x = true;
                }
                arg4.l = false;
                return;
            }
        }
    }

    void d(@NonNull Fragment arg4, @NonNull Bundle arg5, boolean arg6) {
        Fragment v0 = this.v;
        if(v0 != null) {
            k v0_1 = v0.q();
            if((v0_1 instanceof s)) {
                ((s)v0_1).d(arg4, arg5, true);
            }
        }
        for(Object v1: this.r) {
            c v1_1 = (c)v1;
            if((arg6) && !v1_1.b) {
                continue;
            }
            v1_1.a.d(this, arg4, arg5);
        }
    }

    void d(@NonNull Fragment arg4, boolean arg5) {
        Fragment v0 = this.v;
        if(v0 != null) {
            k v0_1 = v0.q();
            if((v0_1 instanceof s)) {
                ((s)v0_1).d(arg4, true);
            }
        }
        for(Object v1: this.r) {
            c v1_1 = (c)v1;
            if((arg5) && !v1_1.b) {
                continue;
            }
            v1_1.a.c(this, arg4);
        }
    }

    private void e(int arg3) {
        try {
            this.g = true;
            this.a(arg3, false);
        }
        catch(Throwable v3) {
            this.g = false;
            throw v3;
        }
        this.g = false;
        this.r();
    }

    void e(Fragment arg4) {
        if((arg4.n) && !arg4.q) {
            arg4.b(arg4.i(arg4.c), null, arg4.c);
            View v0 = arg4.H;
            if(v0 != null) {
                arg4.I = v0;
                v0.setSaveFromParentEnabled(false);
                if(arg4.z) {
                    arg4.H.setVisibility(8);
                }
                arg4.a(arg4.H, arg4.c);
                this.a(arg4, arg4.H, arg4.c, false);
                return;
            }
            arg4.I = null;
        }
    }

    void e(@NonNull Fragment arg4, boolean arg5) {
        Fragment v0 = this.v;
        if(v0 != null) {
            k v0_1 = v0.q();
            if((v0_1 instanceof s)) {
                ((s)v0_1).e(arg4, true);
            }
        }
        for(Object v1: this.r) {
            c v1_1 = (c)v1;
            if((arg5) && !v1_1.b) {
                continue;
            }
            v1_1.a.d(this, arg4);
        }
    }

    @Override  // androidx.fragment.app.k
    public boolean e() {
        this.E();
        return this.a(null, -1, 0);
    }

    @NonNull
    v f(@NonNull Fragment arg2) {
        return this.I.c(arg2);
    }

    void f(@NonNull Fragment arg4, boolean arg5) {
        Fragment v0 = this.v;
        if(v0 != null) {
            k v0_1 = v0.q();
            if((v0_1 instanceof s)) {
                ((s)v0_1).f(arg4, true);
            }
        }
        for(Object v1: this.r) {
            c v1_1 = (c)v1;
            if((arg5) && !v1_1.b) {
                continue;
            }
            v1_1.a.e(this, arg4);
        }
    }

    boolean f() {
        Iterator v0 = this.j.values().iterator();
        boolean v2 = false;
        while(v0.hasNext()) {
            Object v3 = v0.next();
            Fragment v3_1 = (Fragment)v3;
            if(v3_1 != null) {
                v2 = this.w(v3_1);
            }
            if(!v2) {
                continue;
            }
            return 1;
        }
        return 0;
    }

    @NonNull
    androidx.lifecycle.t g(@NonNull Fragment arg2) {
        return this.I.d(arg2);
    }

    public void g() {
        this.y = false;
        this.z = false;
        this.e(2);
    }

    void g(@NonNull Fragment arg4, boolean arg5) {
        Fragment v0 = this.v;
        if(v0 != null) {
            k v0_1 = v0.q();
            if((v0_1 instanceof s)) {
                ((s)v0_1).g(arg4, true);
            }
        }
        for(Object v1: this.r) {
            c v1_1 = (c)v1;
            if((arg5) && !v1_1.b) {
                continue;
            }
            v1_1.a.f(this, arg4);
        }
    }

    public void h() {
        this.y = false;
        this.z = false;
        this.e(1);
    }

    public void h(Fragment arg3) {
        if(s.c) {
            Log.v("FragmentManager", "hide: " + arg3);
        }
        if(!arg3.z) {
            arg3.z = true;
            arg3.O ^= 1;
        }
    }

    void h(@NonNull Fragment arg4, boolean arg5) {
        Fragment v0 = this.v;
        if(v0 != null) {
            k v0_1 = v0.q();
            if((v0_1 instanceof s)) {
                ((s)v0_1).h(arg4, true);
            }
        }
        for(Object v1: this.r) {
            c v1_1 = (c)v1;
            if((arg5) && !v1_1.b) {
                continue;
            }
            v1_1.a.g(this, arg4);
        }
    }

    public void i() {
        this.A = true;
        this.r();
        this.e(0);
        this.t = null;
        this.u = null;
        this.v = null;
        if(this.m != null) {
            this.n.c();
            this.m = null;
        }
    }

    boolean i(@Nullable Fragment arg4) {
        return arg4 == null ? 1 : arg4 == arg4.s.u() && (this.i(arg4.s.v));
    }

    public void j() {
        this.e(1);
    }

    void j(Fragment arg3) {
        if(this.j.get(arg3.f) != null) {
            return;
        }
        this.j.put(arg3.f, arg3);
        if(arg3.C) {
            if(arg3.B) {
                this.a(arg3);
            }
            else {
                this.p(arg3);
            }
            arg3.C = false;
        }
        if(s.c) {
            Log.v("FragmentManager", "Added fragment to active set " + arg3);
        }
    }

    public void k() {
        int v0;
        for(v0 = 0; v0 < this.i.size(); ++v0) {
            Fragment v1 = (Fragment)this.i.get(v0);
            if(v1 != null) {
                v1.Y();
            }
        }
    }

    void k(Fragment arg6) {
        if(this.j.get(arg6.f) == null) {
            return;
        }
        if(s.c) {
            Log.v("FragmentManager", "Removed fragment from active set " + arg6);
        }
        for(Object v1: this.j.values()) {
            Fragment v1_1 = (Fragment)v1;
            if(v1_1 == null || !arg6.f.equals(v1_1.i)) {
                continue;
            }
            v1_1.h = arg6;
            v1_1.i = null;
        }
        this.j.put(arg6.f, null);
        this.p(arg6);
        String v0_1 = arg6.i;
        if(v0_1 != null) {
            arg6.h = (Fragment)this.j.get(v0_1);
        }
        arg6.F();
    }

    public void l() {
        this.e(3);
    }

    void l(Fragment arg11) {
        if(arg11 == null) {
            return;
        }
        if(!this.j.containsKey(arg11.f)) {
            if(s.c) {
                Log.v("FragmentManager", "Ignoring moving " + arg11 + " to state " + this.s + "since it is not added to " + this);
            }
            return;
        }
        int v0 = this.s;
        if(arg11.m) {
            v0 = arg11.I() ? Math.min(v0, 1) : Math.min(v0, 0);
        }
        this.a(arg11, v0, arg11.t(), arg11.u(), false);
        if(arg11.H != null) {
            Fragment v0_1 = this.v(arg11);
            if(v0_1 != null) {
                View v0_2 = v0_1.H;
                ViewGroup v1 = arg11.G;
                int v0_3 = v1.indexOfChild(v0_2);
                int v4 = v1.indexOfChild(arg11.H);
                if(v4 < v0_3) {
                    v1.removeViewAt(v4);
                    v1.addView(arg11.H, v0_3);
                }
            }
            if((arg11.N) && arg11.G != null) {
                float v0_4 = arg11.P;
                if(v0_4 > 0f) {
                    arg11.H.setAlpha(v0_4);
                }
                arg11.P = 0f;
                arg11.N = false;
                a v0_5 = this.a(arg11, arg11.t(), true, arg11.u());
                if(v0_5 != null) {
                    Animation v1_1 = v0_5.a;
                    if(v1_1 == null) {
                        v0_5.b.setTarget(arg11.H);
                        v0_5.b.start();
                    }
                    else {
                        arg11.H.startAnimation(v1_1);
                    }
                }
            }
        }
        if(arg11.O) {
            this.c(arg11);
        }
    }

    void m() {
        this.I();
        this.u(this.w);
    }

    void m(Fragment arg7) {
        this.a(arg7, this.s, 0, 0, false);
    }

    public void n() {
        this.y = false;
        this.z = false;
        this.e(4);
    }

    public void n(Fragment arg8) {
        if(arg8.J) {
            if(this.g) {
                this.B = true;
                return;
            }
            arg8.J = false;
            this.a(arg8, this.s, 0, 0, false);
        }
    }

    public void o() {
        this.y = false;
        this.z = false;
        this.e(3);
    }

    public void o(Fragment arg4) {
        if(s.c) {
            Log.v("FragmentManager", "remove: " + arg4 + " nesting=" + arg4.r);
        }
        if(!arg4.A || (arg4.I() ^ 1) != 0) {
            ArrayList v0 = this.i;
            synchronized(v0) {
                this.i.remove(arg4);
            }
            if(this.w(arg4)) {
                this.x = true;
            }
            arg4.l = false;
            arg4.m = true;
        }
    }

    @Override  // android.view.LayoutInflater$Factory2
    @Nullable
    public View onCreateView(@Nullable View arg13, @NonNull String arg14, @NonNull Context arg15, @NonNull AttributeSet arg16) {
        Fragment v11;
        s v6 = this;
        AttributeSet v0 = arg16;
        Fragment v2 = null;
        if(!"fragment".equals(arg14)) {
            return null;
        }
        String v1 = v0.getAttributeValue(null, "class");
        TypedArray v3 = arg15.obtainStyledAttributes(v0, d.a);
        int v5 = 0;
        if(v1 == null) {
            v1 = v3.getString(0);
        }
        String v7 = v1;
        int v9 = v3.getResourceId(1, -1);
        String v10 = v3.getString(2);
        v3.recycle();
        if(v7 != null && (i.b(arg15.getClassLoader(), v7))) {
            if(arg13 != null) {
                v5 = arg13.getId();
            }
            if(v5 == -1 && v9 == -1 && v10 == null) {
                throw new IllegalArgumentException(arg16.getPositionDescription() + ": Must specify unique android:id, android:tag, or have a parent with an id for " + v7);
            }
            if(v9 != -1) {
                v2 = this.a(v9);
            }
            if(v2 == null && v10 != null) {
                v2 = this.a(v10);
            }
            if(v2 == null && v5 != -1) {
                v2 = this.a(v5);
            }
            if(s.c) {
                Log.v("FragmentManager", "onCreateView: id=0x" + Integer.toHexString(v9) + " fname=" + v7 + " existing=" + v2);
            }
            if(v2 == null) {
                Fragment v1_1 = this.c().a(arg15.getClassLoader(), v7);
                v1_1.n = true;
                v1_1.w = v9 == 0 ? v5 : v9;
                v1_1.x = v5;
                v1_1.y = v10;
                v1_1.o = true;
                v1_1.s = v6;
                j v2_1 = v6.t;
                v1_1.t = v2_1;
                v1_1.a(v2_1.f(), v0, v1_1.c);
                this.a(v1_1, true);
                v11 = v1_1;
            }
            else {
                if(v2.o) {
                    throw new IllegalArgumentException(arg16.getPositionDescription() + ": Duplicate id 0x" + Integer.toHexString(v9) + ", tag " + v10 + ", or parent id 0x" + Integer.toHexString(v5) + " with another fragment for " + v7);
                }
                v2.o = true;
                j v1_2 = v6.t;
                v2.t = v1_2;
                v2.a(v1_2.f(), v0, v2.c);
                v11 = v2;
            }
            if(v6.s < 1 && (v11.n)) {
                this.a(v11, 1, 0, 0, false);
            }
            else {
                this.m(v11);
            }
            View v0_1 = v11.H;
            if(v0_1 != null) {
                if(v9 != 0) {
                    v0_1.setId(v9);
                }
                if(v11.H.getTag() == null) {
                    v11.H.setTag(v10);
                }
                return v11.H;
            }
            throw new IllegalStateException("Fragment " + v7 + " did not create a view.");
        }
        return null;
    }

    @Override  // android.view.LayoutInflater$Factory
    public View onCreateView(String arg2, Context arg3, AttributeSet arg4) {
        return this.onCreateView(null, arg2, arg3, arg4);
    }

    public void p() {
        this.z = true;
        this.e(2);
    }

    void p(@NonNull Fragment arg4) {
        if(this.x()) {
            if(s.c) {
                Log.v("FragmentManager", "Ignoring removeRetainedFragment as the state is already saved");
            }
            return;
        }
        if((this.I.e(arg4)) && (s.c)) {
            Log.v("FragmentManager", "Updating retained Fragments: Removed " + arg4);
        }
    }

    Bundle q(Fragment arg4) {
        Bundle v0;
        if(this.F == null) {
            this.F = new Bundle();
        }
        arg4.j(this.F);
        this.d(arg4, this.F, false);
        if(this.F.isEmpty()) {
            v0 = null;
        }
        else {
            v0 = this.F;
            this.F = null;
        }
        if(arg4.H != null) {
            this.r(arg4);
        }
        if(arg4.d != null) {
            if(v0 == null) {
                v0 = new Bundle();
            }
            v0.putSparseParcelableArray("android:view_state", arg4.d);
        }
        if(!arg4.K) {
            if(v0 == null) {
                v0 = new Bundle();
            }
            v0.putBoolean("android:user_visible_hint", arg4.K);
        }
        return v0;
    }

    void q() {
        if(this.B) {
            this.B = false;
            this.C();
        }
    }

    void r(Fragment arg3) {
        if(arg3.I == null) {
            return;
        }
        SparseArray v0 = this.G;
        if(v0 == null) {
            this.G = new SparseArray();
        }
        else {
            v0.clear();
        }
        arg3.I.saveHierarchyState(this.G);
        if(this.G.size() > 0) {
            arg3.d = this.G;
            this.G = null;
        }
    }

    public boolean r() {
        this.c(true);
        boolean v1;
        for(v1 = false; this.b(this.C, this.D); v1 = true) {
            this.g = true;
            try {
                this.c(this.C, this.D);
            }
            catch(Throwable v0) {
                this.F();
                throw v0;
            }
            this.F();
        }
        this.I();
        this.q();
        this.D();
        return v1;
    }

    public int s() {
        return this.k == null ? 0 : this.k.size();
    }

    public void s(Fragment arg4) {
        if(arg4 != null && (this.j.get(arg4.f) != arg4 || arg4.t != null && arg4.q() != this)) {
            throw new IllegalArgumentException("Fragment " + arg4 + " is not an active fragment of FragmentManager " + this);
        }
        Fragment v0 = this.w;
        this.w = arg4;
        this.u(v0);
        this.u(this.w);
    }

    LayoutInflater.Factory2 t() {
        return this;
    }

    public void t(Fragment arg3) {
        if(s.c) {
            Log.v("FragmentManager", "show: " + arg3);
        }
        if(arg3.z) {
            arg3.z = false;
            arg3.O ^= 1;
        }
    }

    @Override
    public String toString() {
        StringBuilder v0 = new StringBuilder(0x80);
        v0.append("FragmentManager{");
        v0.append(Integer.toHexString(System.identityHashCode(this)));
        v0.append(" in ");
        Fragment v1 = this.v;
        if(v1 == null) {
            a.g.f.a.a(this.t, v0);
        }
        else {
            a.g.f.a.a(v1, v0);
        }
        v0.append("}}");
        return v0.toString();
    }

    private void u(@Nullable Fragment arg3) {
        if(arg3 != null && this.j.get(arg3.f) == arg3) {
            arg3.aa();
        }
    }

    @Nullable
    public Fragment u() {
        return this.w;
    }

    private Fragment v(Fragment arg5) {
        ViewGroup v0 = arg5.G;
        if(v0 != null && arg5.H != null) {
            int v5;
            for(v5 = this.i.indexOf(arg5) - 1; v5 >= 0; --v5) {
                Fragment v1 = (Fragment)this.i.get(v5);
                if(v1.G == v0 && v1.H != null) {
                    return v1;
                }
            }
        }
        return null;
    }

    void v() {
        this.r();
        if(this.n.b()) {
            this.e();
            return;
        }
        this.m.a();
    }

    private boolean w(Fragment arg2) {
        return (arg2.D) && (arg2.E) || (arg2.u.f());
    }

    public boolean w() {
        return this.A;
    }

    public boolean x() {
        return (this.y) || (this.z);
    }

    public void y() {
        int v0 = 0;
        this.y = false;
        this.z = false;
        int v1 = this.i.size();
        while(v0 < v1) {
            Fragment v2 = (Fragment)this.i.get(v0);
            if(v2 != null) {
                v2.L();
            }
            ++v0;
        }
    }

    void z() {
        if(this.q != null) {
            int v0;
            for(v0 = 0; v0 < this.q.size(); ++v0) {
                ((androidx.fragment.app.k.c)this.q.get(v0)).onBackStackChanged();
            }
        }
    }
}

