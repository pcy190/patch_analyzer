// Decompiled by JEB v3.19.1.202005071620

package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
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
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

final class r extends k implements LayoutInflater.Factory2 {
    static class a extends b {
        View a;

        a(View arg1, Animation.AnimationListener arg2) {
            super(arg2);
            this.a = arg1;
        }

        @Override  // androidx.fragment.app.r$b
        @CallSuper
        public void onAnimationEnd(Animation arg4) {
            if(!ViewCompat.w(this.a) && Build.VERSION.SDK_INT < 24) {
                this.a.setLayerType(0, null);
            }
            else {
                this.a.post(new q(this));
            }
            super.onAnimationEnd(arg4);
        }
    }

    static class b implements Animation.AnimationListener {
        private final Animation.AnimationListener a;

        b(Animation.AnimationListener arg1) {
            this.a = arg1;
        }

        @Override  // android.view.animation.Animation$AnimationListener
        @CallSuper
        public void onAnimationEnd(Animation arg2) {
            Animation.AnimationListener v0 = this.a;
            if(v0 != null) {
                v0.onAnimationEnd(arg2);
            }
        }

        @Override  // android.view.animation.Animation$AnimationListener
        @CallSuper
        public void onAnimationRepeat(Animation arg2) {
            Animation.AnimationListener v0 = this.a;
            if(v0 != null) {
                v0.onAnimationRepeat(arg2);
            }
        }

        @Override  // android.view.animation.Animation$AnimationListener
        @CallSuper
        public void onAnimationStart(Animation arg2) {
            Animation.AnimationListener v0 = this.a;
            if(v0 != null) {
                v0.onAnimationStart(arg2);
            }
        }
    }

    static class c {
        public final Animation a;
        public final Animator b;

        c(Animator arg2) {
            this.a = null;
            this.b = arg2;
            if(arg2 != null) {
                return;
            }
            throw new IllegalStateException("Animator cannot be null");
        }

        c(Animation arg2) {
            this.a = arg2;
            this.b = null;
            if(arg2 != null) {
                return;
            }
            throw new IllegalStateException("Animation cannot be null");
        }
    }

    static class d extends AnimatorListenerAdapter {
        View a;

        d(View arg1) {
            this.a = arg1;
        }

        @Override  // android.animation.AnimatorListenerAdapter
        public void onAnimationEnd(Animator arg4) {
            this.a.setLayerType(0, null);
            arg4.removeListener(this);
        }

        @Override  // android.animation.AnimatorListenerAdapter
        public void onAnimationStart(Animator arg3) {
            this.a.setLayerType(2, null);
        }
    }

    static class e extends AnimationSet implements Runnable {
        private final ViewGroup a;
        private final View b;
        private boolean c;
        private boolean d;
        private boolean e;

        e(@NonNull Animation arg2, @NonNull ViewGroup arg3, @NonNull View arg4) {
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
                L.a(this.a, this);
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
                L.a(this.a, this);
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

    static final class f {
        final androidx.fragment.app.k.b a;
        final boolean b;

    }

    static class g {
        public static final int[] a;

        static {
            g.a = new int[]{0x1010003, 0x10100D0, 0x10100D1};
        }
    }

    interface h {
        boolean a(ArrayList arg1, ArrayList arg2);
    }

    static class i implements androidx.fragment.app.Fragment.c {
        final boolean a;
        final androidx.fragment.app.a b;
        private int c;

        i(androidx.fragment.app.a arg1, boolean arg2) {
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
            this.b.a.x();
        }

        public void c() {
            this.b.a.a(this.b, this.a, false, false);
        }

        public void d() {
            int v1 = 0;
            int v0 = this.c <= 0 ? 0 : 1;
            r v3 = this.b.a;
            int v4 = v3.j.size();
            while(v1 < v4) {
                Fragment v5 = (Fragment)v3.j.get(v1);
                v5.a(null);
                if(v0 != 0 && (v5.E())) {
                    v5.Z();
                }
                ++v1;
            }
            this.b.a.a(this.b, this.a, v0 ^ 1, true);
        }

        public boolean e() {
            return this.c == 0;
        }
    }

    String A;
    boolean B;
    ArrayList C;
    ArrayList D;
    ArrayList E;
    Bundle F;
    SparseArray G;
    ArrayList H;
    s I;
    Runnable J;
    static boolean a = false;
    static Field b;
    static final Interpolator c;
    static final Interpolator d;
    static final Interpolator e;
    static final Interpolator f;
    ArrayList g;
    boolean h;
    int i;
    final ArrayList j;
    SparseArray k;
    ArrayList l;
    ArrayList m;
    ArrayList n;
    ArrayList o;
    ArrayList p;
    private final CopyOnWriteArrayList q;
    int r;
    j s;
    androidx.fragment.app.h t;
    Fragment u;
    @Nullable
    Fragment v;
    boolean w;
    boolean x;
    boolean y;
    boolean z;

    static {
        r.c = new DecelerateInterpolator(2.5f);
        r.d = new DecelerateInterpolator(1.5f);
        r.e = new AccelerateInterpolator(2.5f);
        r.f = new AccelerateInterpolator(1.5f);
    }

    r() {
        this.i = 0;
        this.j = new ArrayList();
        this.q = new CopyOnWriteArrayList();
        this.r = 0;
        this.F = null;
        this.G = null;
        this.J = new l(this);
    }

    private void A() {
        if(!this.d()) {
            if(this.A == null) {
                return;
            }
            throw new IllegalStateException("Can not perform this action inside of " + this.A);
        }
        throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
    }

    private void B() {
        this.h = false;
        this.D.clear();
        this.C.clear();
    }

    private void C() {
        int v1 = 0;
        int v0 = this.k == null ? 0 : this.k.size();
        while(v1 < v0) {
            Fragment v4 = (Fragment)this.k.valueAt(v1);
            if(v4 != null) {
                if(v4.g() != null) {
                    int v5 = v4.x();
                    View v2 = v4.g();
                    Animation v3 = v2.getAnimation();
                    if(v3 != null) {
                        v3.cancel();
                        v2.clearAnimation();
                    }
                    v4.a(null);
                    this.a(v4, v5, 0, 0, false);
                }
                else if(v4.h() != null) {
                    v4.h().end();
                }
            }
            ++v1;
        }
    }

    private void D() {
        if(this.H != null) {
            while(!this.H.isEmpty()) {
                ((i)this.H.remove(0)).d();
            }
        }
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
                i v4 = new i(v2, v3);
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

    private static Animation.AnimationListener a(Animation arg3) {
        try {
            if(r.b == null) {
                r.b = Animation.class.getDeclaredField("mListener");
                r.b.setAccessible(true);
            }
            return (Animation.AnimationListener)r.b.get(arg3);
        }
        catch(NoSuchFieldException v3_1) {
            Log.e("FragmentManager", "No field with the name mListener is found in Animation class", v3_1);
            return null;
        }
        catch(IllegalAccessException v3) {
            Log.e("FragmentManager", "Cannot access Animation\'s mListener field", v3);
            return null;
        }
    }

    static c a(Context arg0, float arg1, float arg2) {
        AlphaAnimation v0 = new AlphaAnimation(arg1, arg2);
        v0.setInterpolator(r.d);
        v0.setDuration(220L);
        return new c(v0);
    }

    static c a(Context arg10, float arg11, float arg12, float arg13, float arg14) {
        AnimationSet v10 = new AnimationSet(false);
        ScaleAnimation v0 = new ScaleAnimation(arg11, arg12, arg11, arg12, 1, 0.5f, 1, 0.5f);
        v0.setInterpolator(r.c);
        v0.setDuration(220L);
        v10.addAnimation(v0);
        AlphaAnimation v0_1 = new AlphaAnimation(arg13, arg14);
        v0_1.setInterpolator(r.d);
        v0_1.setDuration(220L);
        v10.addAnimation(v0_1);
        return new c(v10);
    }

    private void a(a.d.d arg11) {
        int v0 = this.r;
        if(v0 < 1) {
            return;
        }
        int v0_1 = Math.min(v0, 3);
        int v1 = this.j.size();
        int v8;
        for(v8 = 0; v8 < v1; ++v8) {
            Fragment v9 = (Fragment)this.j.get(v8);
            if(v9.c < v0_1) {
                this.a(v9, v0_1, v9.p(), v9.q(), false);
                if(v9.K != null && !v9.C && (v9.P)) {
                    arg11.add(v9);
                }
            }
        }
    }

    private void a(@NonNull Fragment arg5, @NonNull c arg6, int arg7) {
        View v0 = arg5.K;
        ViewGroup v1 = arg5.J;
        v1.startViewTransition(v0);
        arg5.b(arg7);
        Animation v7 = arg6.a;
        if(v7 != null) {
            e v2 = new e(v7, v1, v0);
            arg5.a(arg5.K);
            v2.setAnimationListener(new n(this, r.a(v2), v1, arg5));
            r.b(v0, arg6);
            arg5.K.startAnimation(v2);
            return;
        }
        Animator v7_1 = arg6.b;
        arg5.a(v7_1);
        v7_1.addListener(new o(this, v1, v0, arg5));
        v7_1.setTarget(arg5.K);
        r.b(arg5.K, arg6);
        v7_1.start();
    }

    private static void a(s arg3) {
        if(arg3 == null) {
            return;
        }
        List v0 = arg3.b();
        if(v0 != null) {
            for(Object v1: v0) {
                ((Fragment)v1).F = true;
            }
        }
        List v3 = arg3.a();
        if(v3 != null) {
            for(Object v0_2: v3) {
                r.a(((s)v0_2));
            }
        }
    }

    private void a(RuntimeException arg8) {
        Log.e("FragmentManager", arg8.getMessage());
        Log.e("FragmentManager", "Activity state:");
        PrintWriter v2 = new PrintWriter(new a.g.f.b("FragmentManager"));
        j v0 = this.s;
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
            i v3 = (i)this.H.get(v0);
            if(arg8 != null && !v3.a) {
                int v5 = arg8.indexOf(v3.b);
                if(v5 == -1 || !((Boolean)arg9.get(v5)).booleanValue()) {
                    goto label_25;
                }
                v3.c();
            }
            else {
            label_25:
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

    static boolean a(Animator arg5) {
        if(arg5 == null) {
            return 0;
        }
        if((arg5 instanceof ValueAnimator)) {
            PropertyValuesHolder[] v5 = ((ValueAnimator)arg5).getValues();
            int v1;
            for(v1 = 0; v1 < v5.length; ++v1) {
                if("alpha".equals(v5[v1].getPropertyName())) {
                    return 1;
                }
            }
        }
        else if((arg5 instanceof AnimatorSet)) {
            ArrayList v5_1 = ((AnimatorSet)arg5).getChildAnimations();
            int v1_1;
            for(v1_1 = 0; v1_1 < v5_1.size(); ++v1_1) {
                if(r.a(((Animator)v5_1.get(v1_1)))) {
                    return 1;
                }
            }
        }
        return 0;
    }

    static boolean a(View arg3, c arg4) {
        return arg3 != null && arg4 != null && Build.VERSION.SDK_INT >= 19 && arg3.getLayerType() == 0 && (ViewCompat.t(arg3)) && (r.a(arg4));
    }

    static boolean a(c arg4) {
        Animation v0 = arg4.a;
        if((v0 instanceof AlphaAnimation)) {
            return 1;
        }
        if((v0 instanceof AnimationSet)) {
            List v4 = ((AnimationSet)v0).getAnimations();
            int v1;
            for(v1 = 0; v1 < v4.size(); ++v1) {
                if((v4.get(v1) instanceof AlphaAnimation)) {
                    return 1;
                }
            }
            return 0;
        }
        return r.a(arg4.b);
    }

    private boolean a(String arg9, int arg10, int arg11) {
        this.p();
        this.c(true);
        Fragment v1 = this.v;
        if(v1 != null && arg10 < 0 && arg9 == null) {
            k v1_1 = v1.P();
            if(v1_1 != null && (v1_1.e())) {
                return 1;
            }
        }
        boolean v9 = this.a(this.C, this.D, arg9, arg10, arg11);
        if(v9) {
            this.h = true;
            try {
                this.c(this.C, this.D);
            }
            catch(Throwable v9_1) {
                this.B();
                throw v9_1;
            }
            this.B();
        }
        this.o();
        this.z();
        return v9;
    }

    @Nullable
    public Fragment a(int arg4) {
        int v0;
        for(v0 = this.j.size() - 1; v0 >= 0; --v0) {
            Fragment v1 = (Fragment)this.j.get(v0);
            if(v1 != null && v1.z == arg4) {
                return v1;
            }
        }
        SparseArray v0_1 = this.k;
        if(v0_1 != null) {
            int v0_2;
            for(v0_2 = v0_1.size() - 1; v0_2 >= 0; --v0_2) {
                Fragment v1_1 = (Fragment)this.k.valueAt(v0_2);
                if(v1_1 != null && v1_1.z == arg4) {
                    return v1_1;
                }
            }
        }
        return null;
    }

    @Nullable
    public Fragment a(Bundle arg5, String arg6) {
        int v5 = arg5.getInt(arg6, -1);
        if(v5 == -1) {
            return null;
        }
        Fragment v0 = (Fragment)this.k.get(v5);
        if(v0 != null) {
            return v0;
        }
        this.a(new IllegalStateException("Fragment no longer exists for key " + arg6 + ": index " + v5));
        throw null;
    }

    @Override  // androidx.fragment.app.k
    @Nullable
    public Fragment a(@Nullable String arg4) {
        if(arg4 != null) {
            int v0;
            for(v0 = this.j.size() - 1; v0 >= 0; --v0) {
                Fragment v1 = (Fragment)this.j.get(v0);
                if(v1 != null && (arg4.equals(v1.B))) {
                    return v1;
                }
            }
        }
        SparseArray v0_1 = this.k;
        if(v0_1 != null && arg4 != null) {
            int v0_2;
            for(v0_2 = v0_1.size() - 1; v0_2 >= 0; --v0_2) {
                Fragment v1_1 = (Fragment)this.k.valueAt(v0_2);
                if(v1_1 != null && (arg4.equals(v1_1.B))) {
                    return v1_1;
                }
            }
        }
        return null;
    }

    c a(Fragment arg5, int arg6, boolean arg7, int arg8) {
        int v0 = arg5.p();
        Animation v1 = arg5.a(arg6, arg7, v0);
        if(v1 != null) {
            return new c(v1);
        }
        Animator v5 = arg5.b(arg6, arg7, v0);
        if(v5 != null) {
            return new c(v5);
        }
        if(v0 != 0) {
            boolean v5_1 = "anim".equals(this.s.c().getResources().getResourceTypeName(v0));
            int v1_1 = 0;
            if(v5_1) {
                try {
                    Animation v2 = AnimationUtils.loadAnimation(this.s.c(), v0);
                    if(v2 != null) {
                        return new c(v2);
                    }
                    v1_1 = 1;
                }
                catch(Resources.NotFoundException v5_2) {
                    throw v5_2;
                    v1_1 = 1;
                }
                catch(RuntimeException unused_ex) {
                label_31:
                    if(v1_1 == 0) {
                        try {
                            Animator v1_3 = AnimatorInflater.loadAnimator(this.s.c(), v0);
                            if(v1_3 != null) {
                                return new c(v1_3);
                            }
                        }
                        catch(RuntimeException v1_2) {
                            if(v5_1) {
                                throw v1_2;
                            }
                            Animation v5_3 = AnimationUtils.loadAnimation(this.s.c(), v0);
                            if(v5_3 != null) {
                                return new c(v5_3);
                                throw v1_2;
                            }
                        }
                    }
                    goto label_49;
                }
            }
            goto label_31;
        }
    label_49:
        if(arg6 == 0) {
            return null;
        }
        int v6 = r.b(arg6, arg7);
        if(v6 < 0) {
            return null;
        }
        switch(v6) {
            case 1: {
                return r.a(this.s.c(), 1.125f, 1f, 0f, 1f);
            }
            case 2: {
                return r.a(this.s.c(), 1f, 0.975f, 1f, 0f);
            }
            case 3: {
                return r.a(this.s.c(), 0.975f, 1f, 0f, 1f);
            }
            case 4: {
                return r.a(this.s.c(), 1f, 1.075f, 1f, 0f);
            }
            case 5: {
                return r.a(this.s.c(), 0f, 1f);
            }
            case 6: {
                return r.a(this.s.c(), 1f, 0f);
            }
            default: {
                if(arg8 == 0 && (this.s.h())) {
                    this.s.g();
                    return null;
                }
                return null;
            }
        }
    }

    @Override  // androidx.fragment.app.k
    public w a() {
        return new androidx.fragment.app.a(this);
    }

    public void a(int arg5, androidx.fragment.app.a arg6) {
        synchronized(this) {
            if(this.n == null) {
                this.n = new ArrayList();
            }
            int v0 = this.n.size();
            if(arg5 < v0) {
                if(r.a) {
                    Log.v("FragmentManager", "Setting back stack index " + arg5 + " to " + arg6);
                }
                this.n.set(arg5, arg6);
            }
            else {
                while(v0 < arg5) {
                    this.n.add(null);
                    if(this.o == null) {
                        this.o = new ArrayList();
                    }
                    if(r.a) {
                        Log.v("FragmentManager", "Adding available back stack index " + v0);
                    }
                    this.o.add(Integer.valueOf(v0));
                    ++v0;
                }
                if(r.a) {
                    Log.v("FragmentManager", "Adding back stack index " + arg5 + " with " + arg6);
                }
                this.n.add(arg6);
            }
            return;
        }
    }

    void a(int arg4, boolean arg5) {
        if(this.s == null && arg4 != 0) {
            throw new IllegalStateException("No activity");
        }
        if(!arg5 && arg4 == this.r) {
            return;
        }
        this.r = arg4;
        if(this.k != null) {
            int v4 = this.j.size();
            int v0;
            for(v0 = 0; v0 < v4; ++v0) {
                this.h(((Fragment)this.j.get(v0)));
            }
            int v4_1 = this.k.size();
            int v0_1;
            for(v0_1 = 0; v0_1 < v4_1; ++v0_1) {
                Fragment v1 = (Fragment)this.k.valueAt(v0_1);
                if(v1 != null && ((v1.n) || (v1.D)) && !v1.P) {
                    this.h(v1);
                }
            }
            this.y();
            if(this.w) {
                j v4_2 = this.s;
                if(v4_2 != null && this.r == 4) {
                    v4_2.i();
                    this.w = false;
                }
            }
        }
    }

    public void a(Configuration arg3) {
        int v0;
        for(v0 = 0; v0 < this.j.size(); ++v0) {
            Fragment v1 = (Fragment)this.j.get(v0);
            if(v1 != null) {
                v1.a(arg3);
            }
        }
    }

    public void a(Bundle arg2, String arg3, Fragment arg4) {
        int v0 = arg4.g;
        if(v0 >= 0) {
            arg2.putInt(arg3, v0);
            return;
        }
        this.a(new IllegalStateException("Fragment " + arg4 + " is not currently in the FragmentManager"));
        throw null;
    }

    void a(Parcelable arg14, s arg15) {
        List v4;
        List v3;
        if(arg14 == null) {
            return;
        }
        FragmentManagerState v14 = (FragmentManagerState)arg14;
        if(v14.a == null) {
            return;
        }
        if(arg15 == null) {
            v3 = null;
            v4 = null;
        }
        else {
            List v2 = arg15.b();
            v3 = arg15.a();
            v4 = arg15.c();
            int v5 = v2 == null ? 0 : v2.size();
            int v6 = 0;
            while(v6 < v5) {
                Fragment v7 = (Fragment)v2.get(v6);
                if(r.a) {
                    Log.v("FragmentManager", "restoreAllState: re-attaching retained " + v7);
                }
                int v8;
                for(v8 = 0; v8 < v14.a.length && v14.a[v8].b != v7.g; ++v8) {
                }
                FragmentState[] v9 = v14.a;
                if(v8 != v9.length) {
                    FragmentState v8_1 = v9[v8];
                    v8_1.l = v7;
                    v7.e = null;
                    v7.s = 0;
                    v7.p = false;
                    v7.m = false;
                    v7.j = null;
                    Bundle v9_1 = v8_1.k;
                    if(v9_1 != null) {
                        v9_1.setClassLoader(this.s.c().getClassLoader());
                        v7.e = v8_1.k.getSparseParcelableArray("android:view_state");
                        v7.d = v8_1.k;
                    }
                    ++v6;
                    continue;
                }
                this.a(new IllegalStateException("Could not find active fragment with index " + v7.g));
                throw null;
            }
        }
        this.k = new SparseArray(v14.a.length);
        int v2_1;
        for(v2_1 = 0; true; ++v2_1) {
            FragmentState[] v5_1 = v14.a;
            if(v2_1 >= v5_1.length) {
                break;
            }
            FragmentState v5_2 = v5_1[v2_1];
            if(v5_2 != null) {
                Fragment v6_1 = v5_2.a(this.s, this.t, this.u, v3 == null || v2_1 >= v3.size() ? null : ((s)v3.get(v2_1)), v4 == null || v2_1 >= v4.size() ? null : ((androidx.lifecycle.s)v4.get(v2_1)));
                if(r.a) {
                    Log.v("FragmentManager", "restoreAllState: active #" + v2_1 + ": " + v6_1);
                }
                this.k.put(v6_1.g, v6_1);
                v5_2.l = null;
            }
        }
        if(arg15 != null) {
            List v15 = arg15.b();
            int v2_2 = v15 == null ? 0 : v15.size();
            int v3_1;
            for(v3_1 = 0; v3_1 < v2_2; ++v3_1) {
                Fragment v4_1 = (Fragment)v15.get(v3_1);
                int v5_3 = v4_1.k;
                if(v5_3 >= 0) {
                    v4_1.j = (Fragment)this.k.get(v5_3);
                    if(v4_1.j == null) {
                        Log.w("FragmentManager", "Re-attaching retained fragment " + v4_1 + " target no longer exists: " + v4_1.k);
                    }
                }
            }
        }
        this.j.clear();
        if(v14.b != null) {
            int v15_1 = 0;
            while(true) {
            label_165:
                int[] v2_3 = v14.b;
                if(v15_1 >= v2_3.length) {
                    break;
                }
                Fragment v2_4 = (Fragment)this.k.get(v2_3[v15_1]);
                if(v2_4 != null) {
                    v2_4.m = true;
                    if(r.a) {
                        Log.v("FragmentManager", "restoreAllState: added #" + v15_1 + ": " + v2_4);
                    }
                    if(!this.j.contains(v2_4)) {
                        ArrayList v3_2 = this.j;
                        __monitor_enter(v3_2);
                        try {
                            this.j.add(v2_4);
                            __monitor_exit(v3_2);
                            ++v15_1;
                            goto label_165;
                        label_199:
                            __monitor_exit(v3_2);
                        }
                        catch(Throwable v14_1) {
                            goto label_199;
                        }
                        throw v14_1;
                    }
                    throw new IllegalStateException("Already added!");
                }
                this.a(new IllegalStateException("No instantiated fragment for index #" + v14.b[v15_1]));
                throw null;
            }
        }
        BackStackState[] v15_2 = v14.c;
        if(v15_2 == null) {
            this.l = null;
        }
        else {
            this.l = new ArrayList(v15_2.length);
            int v15_3;
            for(v15_3 = 0; true; ++v15_3) {
                BackStackState[] v0 = v14.c;
                if(v15_3 >= v0.length) {
                    break;
                }
                androidx.fragment.app.a v0_1 = v0[v15_3].a(this);
                if(r.a) {
                    Log.v("FragmentManager", "restoreAllState: back stack #" + v15_3 + " (index " + v0_1.m + "): " + v0_1);
                    PrintWriter v3_3 = new PrintWriter(new a.g.f.b("FragmentManager"));
                    v0_1.a("  ", v3_3, false);
                    v3_3.close();
                }
                this.l.add(v0_1);
                int v2_5 = v0_1.m;
                if(v2_5 >= 0) {
                    this.a(v2_5, v0_1);
                }
            }
        }
        int v15_4 = v14.d;
        if(v15_4 >= 0) {
            this.v = (Fragment)this.k.get(v15_4);
        }
        this.i = v14.e;
    }

    public void a(Menu arg3) {
        if(this.r < 1) {
            return;
        }
        int v0;
        for(v0 = 0; v0 < this.j.size(); ++v0) {
            Fragment v1 = (Fragment)this.j.get(v0);
            if(v1 != null) {
                v1.c(arg3);
            }
        }
    }

    public void a(Fragment arg4) {
        if(r.a) {
            Log.v("FragmentManager", "attach: " + arg4);
        }
        if(arg4.D) {
            arg4.D = false;
            if(!arg4.m) {
                if(this.j.contains(arg4)) {
                    throw new IllegalStateException("Fragment already added: " + arg4);
                }
                if(r.a) {
                    Log.v("FragmentManager", "add from attach: " + arg4);
                }
                ArrayList v0 = this.j;
                synchronized(v0) {
                    this.j.add(arg4);
                }
                arg4.m = true;
                if((arg4.G) && (arg4.H)) {
                    this.w = true;
                    return;
                    throw new IllegalStateException("Fragment already added: " + arg4);
                }
            }
        }
    }

    void a(Fragment arg17, int arg18, int arg19, int arg20, boolean arg21) {
        String v0_11;
        ViewGroup v0_10;
        int v0;
        r v6 = this;
        Fragment v7 = arg17;
        boolean v8 = true;
        if((v7.m) && !v7.D) {
            v0 = arg18;
        }
        else {
            v0 = arg18;
            if(v0 > 1) {
                v0 = 1;
            }
        }
        if(v7.n) {
            int v1 = v7.c;
            if(v0 > v1) {
                v0 = v1 != 0 || !arg17.D() ? v7.c : 1;
            }
        }
        int v11 = !v7.M || v7.c >= 3 || v0 <= 2 ? v0 : 2;
        int v0_1 = v7.c;
        if(v0_1 <= v11) {
            if((v7.o) && !v7.p) {
                return;
            }
            if(arg17.g() != null || arg17.h() != null) {
                v7.a(null);
                v7.a(null);
                this.a(arg17, arg17.x(), 0, 0, true);
            }
            int v0_2 = v7.c;
            if(v0_2 == 0) {
                if(v11 > 0) {
                    if(r.a) {
                        Log.v("FragmentManager", "moveto CREATED: " + v7);
                    }
                    Bundle v0_3 = v7.d;
                    if(v0_3 != null) {
                        v0_3.setClassLoader(v6.s.c().getClassLoader());
                        v7.e = v7.d.getSparseParcelableArray("android:view_state");
                        v7.j = v6.a(v7.d, "android:target_state");
                        if(v7.j != null) {
                            v7.l = v7.d.getInt("android:target_req_state", 0);
                        }
                        Boolean v0_4 = v7.f;
                        if(v0_4 == null) {
                            v7.N = v7.d.getBoolean("android:user_visible_hint", true);
                        }
                        else {
                            v7.N = v0_4.booleanValue();
                            v7.f = null;
                        }
                        if(!v7.N) {
                            v7.M = true;
                            if(v11 > 2) {
                                v11 = 2;
                            }
                        }
                    }
                    j v0_5 = v6.s;
                    v7.u = v0_5;
                    Fragment v1_1 = v6.u;
                    v7.y = v1_1;
                    v7.t = v1_1 == null ? v0_5.d() : v1_1.v;
                    Fragment v0_6 = v7.j;
                    if(v0_6 != null) {
                        Object v0_7 = v6.k.get(v0_6.g);
                        Fragment v1_2 = v7.j;
                        if(v0_7 != v1_2) {
                            throw new IllegalStateException("Fragment " + v7 + " declared target fragment " + v7.j + " that does not belong to this FragmentManager!");
                        }
                        if(v1_2.c < 1) {
                            this.a(v1_2, 1, 0, 0, true);
                            goto label_149;
                            throw new IllegalStateException("Fragment " + v7 + " declared target fragment " + v7.j + " that does not belong to this FragmentManager!");
                        }
                    }
                label_149:
                    v6.b(v7, v6.s.c(), false);
                    v7.I = false;
                    v7.a(v6.s.c());
                    if(v7.I) {
                        Fragment v0_8 = v7.y;
                        if(v0_8 == null) {
                            v6.s.a(v7);
                        }
                        else {
                            v0_8.a(v7);
                        }
                        v6.a(v7, v6.s.c(), false);
                        if(v7.T) {
                            v7.k(v7.d);
                            v7.c = 1;
                        }
                        else {
                            v6.c(v7, v7.d, false);
                            v7.h(v7.d);
                            v6.b(v7, v7.d, false);
                        }
                        v7.F = false;
                        goto label_191;
                    }
                    throw new M("Fragment " + v7 + " did not call through to super.onAttach()");
                }
            label_191:
                this.d(arg17);
                if(v11 > 1) {
                    if(r.a) {
                        Log.v("FragmentManager", "moveto ACTIVITY_CREATED: " + v7);
                    }
                    if(!v7.o) {
                        int v0_9 = v7.A;
                        if(v0_9 == 0) {
                            v0_10 = null;
                        }
                        else {
                            if(v0_9 == -1) {
                                v6.a(new IllegalArgumentException("Cannot create fragment " + v7 + " for a container view with no id"));
                                throw null;
                            }
                            v0_10 = (ViewGroup)v6.t.a(v0_9);
                            if(v0_10 == null && !v7.q) {
                                try {
                                    v0_11 = arg17.t().getResourceName(v7.A);
                                }
                                catch(Resources.NotFoundException unused_ex) {
                                    v0_11 = "unknown";
                                }
                                v6.a(new IllegalArgumentException("No view found for id 0x" + Integer.toHexString(v7.A) + " (" + v0_11 + ") for fragment " + v7));
                                throw null;
                                v6.a(new IllegalArgumentException("Cannot create fragment " + v7 + " for a container view with no id"));
                                throw null;
                            }
                        }
                        v7.J = v0_10;
                        v7.b(v7.i(v7.d), v0_10, v7.d);
                        View v1_3 = v7.K;
                        if(v1_3 == null) {
                            v7.L = null;
                        }
                        else {
                            v7.L = v1_3;
                            v1_3.setSaveFromParentEnabled(false);
                            if(v0_10 != null) {
                                v0_10.addView(v7.K);
                            }
                            if(v7.C) {
                                v7.K.setVisibility(8);
                            }
                            v7.a(v7.K, v7.d);
                            v6.a(v7, v7.K, v7.d, false);
                            if(v7.K.getVisibility() != 0 || v7.J == null) {
                                v8 = false;
                            }
                            v7.P = v8;
                        }
                    }
                    v7.g(v7.d);
                    v6.a(v7, v7.d, false);
                    if(v7.K != null) {
                        v7.l(v7.d);
                    }
                    v7.d = null;
                }
            label_295:
                if(v11 > 2) {
                    if(r.a) {
                        Log.v("FragmentManager", "moveto STARTED: " + v7);
                    }
                    arg17.W();
                    v6.f(v7, false);
                }
            label_307:
                if(v11 > 3) {
                    if(r.a) {
                        Log.v("FragmentManager", "moveto RESUMED: " + v7);
                    }
                    arg17.V();
                    v6.e(v7, false);
                    v7.d = null;
                    v7.e = null;
                }
            }
            else {
                if(v0_2 == 1) {
                    goto label_191;
                }
                if(v0_2 == 2) {
                    goto label_295;
                }
                if(v0_2 == 3) {
                    goto label_307;
                }
            }
            v8 = v11;
        }
        else if(v0_1 <= v11) {
            v8 = v11;
        }
        else if(v0_1 == 1) {
        label_415:
            if(v11 < 1) {
                if(v6.z) {
                    if(arg17.g() != null) {
                        View v0_14 = arg17.g();
                        v7.a(null);
                        v0_14.clearAnimation();
                    }
                    else if(arg17.h() != null) {
                        Animator v0_15 = arg17.h();
                        v7.a(null);
                        v0_15.cancel();
                    }
                }
                if(arg17.g() == null && arg17.h() == null) {
                    if(r.a) {
                        Log.v("FragmentManager", "movefrom CREATED: " + v7);
                    }
                    if(v7.F) {
                        v7.c = 0;
                    }
                    else {
                        arg17.Q();
                        v6.b(v7, false);
                    }
                    arg17.S();
                    v6.c(v7, false);
                    if(arg21) {
                    }
                    else if(v7.F) {
                        v7.u = null;
                        v7.y = null;
                        v7.t = null;
                    }
                    else {
                        this.g(arg17);
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
            if(v0_1 != 2) {
                if(v0_1 != 3) {
                    if(v0_1 != 4) {
                        v8 = v11;
                        goto label_472;
                    }
                    if(v11 < 4) {
                        if(r.a) {
                            Log.v("FragmentManager", "movefrom RESUMED: " + v7);
                        }
                        arg17.U();
                        v6.d(v7, false);
                        goto label_348;
                        v8 = v11;
                        goto label_472;
                    }
                }
            label_348:
                if(v11 < 3) {
                    if(r.a) {
                        Log.v("FragmentManager", "movefrom STARTED: " + v7);
                    }
                    arg17.X();
                    v6.g(v7, false);
                }
            }
            if(v11 < 2) {
                if(r.a) {
                    Log.v("FragmentManager", "movefrom ACTIVITY_CREATED: " + v7);
                }
                if(v7.K != null && (v6.s.b(v7)) && v7.e == null) {
                    this.m(arg17);
                }
                arg17.R();
                v6.h(v7, false);
                View v0_12 = v7.K;
                if(v0_12 != null) {
                    ViewGroup v1_4 = v7.J;
                    if(v1_4 != null) {
                        v1_4.endViewTransition(v0_12);
                        v7.K.clearAnimation();
                        c v0_13 = v6.r <= 0 || (v6.z) || v7.K.getVisibility() != 0 || v7.R < 0f ? null : v6.a(v7, arg19, false, arg20);
                        v7.R = 0f;
                        if(v0_13 != null) {
                            v6.a(v7, v0_13, v11);
                        }
                        v7.J.removeView(v7.K);
                    }
                }
                v7.J = null;
                v7.K = null;
                v7.W = null;
                v7.X.a(null);
                v7.L = null;
                v7.p = false;
            }
            goto label_415;
        }
    label_472:
        if(v7.c != ((int)v8)) {
            Log.w("FragmentManager", "moveToState: Fragment state for " + v7 + " not updated inline; " + "expected state " + ((int)v8) + " found " + v7.c);
            v7.c = (int)v8;
        }
    }

    void a(@NonNull Fragment arg4, @NonNull Context arg5, boolean arg6) {
        Fragment v0 = this.u;
        if(v0 != null) {
            k v0_1 = v0.o();
            if((v0_1 instanceof r)) {
                ((r)v0_1).a(arg4, arg5, true);
            }
        }
        for(Object v1: this.q) {
            f v1_1 = (f)v1;
            if((arg6) && !v1_1.b) {
                continue;
            }
            v1_1.a.a(this, arg4, arg5);
        }
    }

    void a(@NonNull Fragment arg4, @Nullable Bundle arg5, boolean arg6) {
        Fragment v0 = this.u;
        if(v0 != null) {
            k v0_1 = v0.o();
            if((v0_1 instanceof r)) {
                ((r)v0_1).a(arg4, arg5, true);
            }
        }
        for(Object v1: this.q) {
            f v1_1 = (f)v1;
            if((arg6) && !v1_1.b) {
                continue;
            }
            v1_1.a.a(this, arg4, arg5);
        }
    }

    void a(@NonNull Fragment arg4, @NonNull View arg5, @Nullable Bundle arg6, boolean arg7) {
        Fragment v0 = this.u;
        if(v0 != null) {
            k v0_1 = v0.o();
            if((v0_1 instanceof r)) {
                ((r)v0_1).a(arg4, arg5, arg6, true);
            }
        }
        for(Object v1: this.q) {
            f v1_1 = (f)v1;
            if((arg7) && !v1_1.b) {
                continue;
            }
            v1_1.a.a(this, arg4, arg5, arg6);
        }
    }

    public void a(Fragment arg4, boolean arg5) {
        if(r.a) {
            Log.v("FragmentManager", "add: " + arg4);
        }
        this.f(arg4);
        if(!arg4.D) {
            if(this.j.contains(arg4)) {
                throw new IllegalStateException("Fragment already added: " + arg4);
            }
            ArrayList v0 = this.j;
            synchronized(v0) {
                this.j.add(arg4);
            }
            arg4.m = true;
            arg4.n = false;
            if(arg4.K == null) {
                arg4.Q = false;
            }
            if((arg4.G) && (arg4.H)) {
                this.w = true;
            }
            if(arg5) {
                this.i(arg4);
                return;
                throw new IllegalStateException("Fragment already added: " + arg4);
            }
        }
    }

    void a(androidx.fragment.app.a arg2) {
        if(this.l == null) {
            this.l = new ArrayList();
        }
        this.l.add(arg2);
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
            B.a(this, v1, v2, 0, 1, true);
        }
        if(arg11) {
            this.a(this.r, true);
        }
        SparseArray v9 = this.k;
        if(v9 != null) {
            int v9_1 = v9.size();
            int v0;
            for(v0 = 0; v0 < v9_1; ++v0) {
                Fragment v1_1 = (Fragment)this.k.valueAt(v0);
                if(v1_1 != null && v1_1.K != null && (v1_1.P) && (arg8.b(v1_1.A))) {
                    float v2_1 = v1_1.R;
                    if(v2_1 > 0f) {
                        v1_1.K.setAlpha(v2_1);
                    }
                    if(arg11) {
                        v1_1.R = 0f;
                    }
                    else {
                        v1_1.R = -1f;
                        v1_1.P = false;
                    }
                }
            }
        }
    }

    public void a(j arg2, androidx.fragment.app.h arg3, Fragment arg4) {
        if(this.s == null) {
            this.s = arg2;
            this.t = arg3;
            this.u = arg4;
            return;
        }
        throw new IllegalStateException("Already attached");
    }

    public void a(h arg2, boolean arg3) {
        if(!arg3) {
            this.A();
        }
        __monitor_enter(this);
        try {
            if(!this.z && this.s != null) {
                if(this.g == null) {
                    this.g = new ArrayList();
                }
                this.g.add(arg2);
                this.x();
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
    public void a(String arg7, FileDescriptor arg8, PrintWriter arg9, String[] arg10) {
        String v0 = arg7 + "    ";
        SparseArray v1 = this.k;
        int v2 = 0;
        if(v1 != null) {
            int v1_1 = v1.size();
            if(v1_1 > 0) {
                arg9.print(arg7);
                arg9.print("Active Fragments in ");
                arg9.print(Integer.toHexString(System.identityHashCode(this)));
                arg9.println(":");
                int v3;
                for(v3 = 0; v3 < v1_1; ++v3) {
                    Fragment v4 = (Fragment)this.k.valueAt(v3);
                    arg9.print(arg7);
                    arg9.print("  #");
                    arg9.print(v3);
                    arg9.print(": ");
                    arg9.println(v4);
                    if(v4 != null) {
                        v4.a(v0, arg8, arg9, arg10);
                    }
                }
            }
        }
        int v1_2 = this.j.size();
        if(v1_2 > 0) {
            arg9.print(arg7);
            arg9.println("Added Fragments:");
            int v3_1;
            for(v3_1 = 0; v3_1 < v1_2; ++v3_1) {
                Fragment v4_1 = (Fragment)this.j.get(v3_1);
                arg9.print(arg7);
                arg9.print("  #");
                arg9.print(v3_1);
                arg9.print(": ");
                arg9.println(v4_1.toString());
            }
        }
        ArrayList v1_3 = this.m;
        if(v1_3 != null) {
            int v1_4 = v1_3.size();
            if(v1_4 > 0) {
                arg9.print(arg7);
                arg9.println("Fragments Created Menus:");
                int v3_2;
                for(v3_2 = 0; v3_2 < v1_4; ++v3_2) {
                    Fragment v4_2 = (Fragment)this.m.get(v3_2);
                    arg9.print(arg7);
                    arg9.print("  #");
                    arg9.print(v3_2);
                    arg9.print(": ");
                    arg9.println(v4_2.toString());
                }
            }
        }
        ArrayList v1_5 = this.l;
        if(v1_5 != null) {
            int v1_6 = v1_5.size();
            if(v1_6 > 0) {
                arg9.print(arg7);
                arg9.println("Back Stack:");
                int v3_3;
                for(v3_3 = 0; v3_3 < v1_6; ++v3_3) {
                    androidx.fragment.app.a v4_3 = (androidx.fragment.app.a)this.l.get(v3_3);
                    arg9.print(arg7);
                    arg9.print("  #");
                    arg9.print(v3_3);
                    arg9.print(": ");
                    arg9.println(v4_3.toString());
                    v4_3.a(v0, arg8, arg9, arg10);
                }
            }
        }
        synchronized(this) {
            if(this.n != null) {
                int v8 = this.n.size();
                if(v8 > 0) {
                    arg9.print(arg7);
                    arg9.println("Back Stack Indices:");
                    int v10;
                    for(v10 = 0; v10 < v8; ++v10) {
                        androidx.fragment.app.a v0_1 = (androidx.fragment.app.a)this.n.get(v10);
                        arg9.print(arg7);
                        arg9.print("  #");
                        arg9.print(v10);
                        arg9.print(": ");
                        arg9.println(v0_1);
                    }
                }
            }
            if(this.o != null && this.o.size() > 0) {
                arg9.print(arg7);
                arg9.print("mAvailBackStackIndices: ");
                arg9.println(Arrays.toString(this.o.toArray()));
            }
        }
        ArrayList v8_1 = this.g;
        if(v8_1 != null) {
            int v8_2 = v8_1.size();
            if(v8_2 > 0) {
                arg9.print(arg7);
                arg9.println("Pending Actions:");
                while(v2 < v8_2) {
                    h v10_1 = (h)this.g.get(v2);
                    arg9.print(arg7);
                    arg9.print("  #");
                    arg9.print(v2);
                    arg9.print(": ");
                    arg9.println(v10_1);
                    ++v2;
                }
            }
        }
        arg9.print(arg7);
        arg9.println("FragmentManager misc state:");
        arg9.print(arg7);
        arg9.print("  mHost=");
        arg9.println(this.s);
        arg9.print(arg7);
        arg9.print("  mContainer=");
        arg9.println(this.t);
        if(this.u != null) {
            arg9.print(arg7);
            arg9.print("  mParent=");
            arg9.println(this.u);
        }
        arg9.print(arg7);
        arg9.print("  mCurState=");
        arg9.print(this.r);
        arg9.print(" mStateSaved=");
        arg9.print(this.x);
        arg9.print(" mStopped=");
        arg9.print(this.y);
        arg9.print(" mDestroyed=");
        arg9.println(this.z);
        if(this.w) {
            arg9.print(arg7);
            arg9.print("  mNeedMenuInvalidate=");
            arg9.println(this.w);
        }
        if(this.A != null) {
            arg9.print(arg7);
            arg9.print("  mNoTransactionsBecause=");
            arg9.println(this.A);
        }
    }

    public void a(boolean arg3) {
        int v0;
        for(v0 = this.j.size() - 1; v0 >= 0; --v0) {
            Fragment v1 = (Fragment)this.j.get(v0);
            if(v1 != null) {
                v1.d(arg3);
            }
        }
    }

    public boolean a(Menu arg8, MenuInflater arg9) {
        int v1 = 0;
        if(this.r < 1) {
            return 0;
        }
        ArrayList v3 = null;
        int v0 = 0;
        boolean v4 = false;
        while(v0 < this.j.size()) {
            Fragment v5 = (Fragment)this.j.get(v0);
            if(v5 != null && (v5.b(arg8, arg9))) {
                if(v3 == null) {
                    v3 = new ArrayList();
                }
                v3.add(v5);
                v4 = true;
            }
            ++v0;
        }
        if(this.m != null) {
            while(v1 < this.m.size()) {
                Fragment v8 = (Fragment)this.m.get(v1);
                if(v3 == null || !v3.contains(v8)) {
                    v8.I();
                }
                ++v1;
            }
        }
        this.m = v3;
        return v4;
    }

    public boolean a(MenuItem arg5) {
        if(this.r < 1) {
            return 0;
        }
        int v0;
        for(v0 = 0; v0 < this.j.size(); ++v0) {
            Fragment v3 = (Fragment)this.j.get(v0);
            if(v3 != null && (v3.c(arg5))) {
                return 1;
            }
        }
        return 0;
    }

    boolean a(ArrayList arg6, ArrayList arg7, String arg8, int arg9, int arg10) {
        int v0_1;
        ArrayList v0 = this.l;
        if(v0 == null) {
            return 0;
        }
        if(arg8 == null && arg9 < 0 && (arg10 & 1) == 0) {
            int v8 = v0.size() - 1;
            if(v8 < 0) {
                return 0;
            }
            arg6.add(this.l.remove(v8));
            arg7.add(Boolean.valueOf(true));
            return 1;
        }
        if(arg8 == null && arg9 < 0) {
            v0_1 = -1;
        }
        else {
            for(v0_1 = this.l.size() - 1; v0_1 >= 0; --v0_1) {
                androidx.fragment.app.a v3 = (androidx.fragment.app.a)this.l.get(v0_1);
                if(arg8 != null && (arg8.equals(v3.c())) || arg9 >= 0 && arg9 == v3.m) {
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
                    androidx.fragment.app.a v10 = (androidx.fragment.app.a)this.l.get(v0_1);
                    if(arg8 != null && (arg8.equals(v10.c())) || arg9 >= 0 && arg9 == v10.m) {
                        goto label_47;
                    }
                    break;
                }
                while(true);
            }
        }
        if(v0_1 == this.l.size() - 1) {
            return 0;
        }
        int v8_1;
        for(v8_1 = this.l.size() - 1; v8_1 > v0_1; --v8_1) {
            arg6.add(this.l.remove(v8_1));
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
            if(!v2.m) {
                View v3 = v2.y();
                v2.R = v3.getAlpha();
                v3.setAlpha(0f);
            }
        }
    }

    private static void b(View arg3, c arg4) {
        if(arg3 != null && arg4 != null && (r.a(arg3, arg4))) {
            Animator v0 = arg4.b;
            if(v0 != null) {
                v0.addListener(new d(arg3));
                return;
            }
            Animation.AnimationListener v0_1 = r.a(arg4.a);
            arg3.setLayerType(2, null);
            arg4.a.setAnimationListener(new a(arg3, v0_1));
        }
    }

    private void b(ArrayList arg16, ArrayList arg17, int arg18, int arg19) {
        int v4;
        int v9 = arg18;
        boolean v11 = ((androidx.fragment.app.a)arg16.get(v9)).t;
        ArrayList v0 = this.E;
        if(v0 == null) {
            this.E = new ArrayList();
        }
        else {
            v0.clear();
        }
        this.E.addAll(this.j);
        Fragment v2 = this.r();
        int v12 = 0;
        int v0_1;
        for(v0_1 = v9; v0_1 < arg19; ++v0_1) {
            androidx.fragment.app.a v3 = (androidx.fragment.app.a)arg16.get(v0_1);
            v2 = ((Boolean)arg17.get(v0_1)).booleanValue() ? v3.b(this.E, v2) : v3.a(this.E, v2);
            v12 = v12 != 0 || (v3.i) ? 1 : 0;
        }
        this.E.clear();
        if(!v11) {
            B.a(this, arg16, arg17, arg18, arg19, false);
        }
        r.a(arg16, arg17, arg18, arg19);
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
            B.a(this, arg16, arg17, arg18, v4, true);
            this.a(this.r, true);
        }
        while(v9 < arg19) {
            androidx.fragment.app.a v0_3 = (androidx.fragment.app.a)arg16.get(v9);
            if(((Boolean)arg17.get(v9)).booleanValue()) {
                int v1 = v0_3.m;
                if(v1 >= 0) {
                    this.b(v1);
                    v0_3.m = -1;
                }
            }
            v0_3.e();
            ++v9;
        }
        if(v12 != 0) {
            this.t();
        }
    }

    private boolean b(ArrayList arg5, ArrayList arg6) {
        synchronized(this) {
            int v1 = 0;
            if(this.g != null && this.g.size() != 0) {
                int v0 = this.g.size();
                int v2 = 0;
                while(v1 < v0) {
                    v2 |= ((h)this.g.get(v1)).a(arg5, arg6);
                    ++v1;
                }
                this.g.clear();
                this.s.e().removeCallbacks(this.J);
                return (boolean)v2;
            }
            return 0;
        }
    }

    public int b(androidx.fragment.app.a arg5) {
        synchronized(this) {
            if(this.o != null && this.o.size() > 0) {
                if(r.a) {
                    Log.v("FragmentManager", "Adding back stack index " + ((int)(((Integer)this.o.remove(this.o.size() - 1)))) + " with " + arg5);
                }
                this.n.set(((Integer)this.o.remove(this.o.size() - 1)).intValue(), arg5);
                return (int)(((Integer)this.o.remove(this.o.size() - 1)));
            }
            if(this.n == null) {
                this.n = new ArrayList();
            }
            int v0 = this.n.size();
            if(r.a) {
                Log.v("FragmentManager", "Setting back stack index " + v0 + " to " + arg5);
            }
            this.n.add(arg5);
            return v0;
        }
    }

    public Fragment b(String arg3) {
        SparseArray v0 = this.k;
        if(v0 != null && arg3 != null) {
            int v0_1;
            for(v0_1 = v0.size() - 1; v0_1 >= 0; --v0_1) {
                Fragment v1 = (Fragment)this.k.valueAt(v0_1);
                if(v1 != null) {
                    Fragment v1_1 = v1.a(arg3);
                    if(v1_1 != null) {
                        return v1_1;
                    }
                }
            }
        }
        return null;
    }

    public void b(int arg4) {
        synchronized(this) {
            this.n.set(arg4, null);
            if(this.o == null) {
                this.o = new ArrayList();
            }
            if(r.a) {
                Log.v("FragmentManager", "Freeing back stack index " + arg4);
            }
            this.o.add(Integer.valueOf(arg4));
            return;
        }
    }

    void b(Fragment arg8) {
        if(arg8.K != null) {
            c v0 = this.a(arg8, arg8.q(), arg8.C ^ 1, arg8.r());
            if(v0 == null) {
            label_35:
                if(v0 != null) {
                    r.b(arg8.K, v0);
                    arg8.K.startAnimation(v0.a);
                    v0.a.start();
                }
                arg8.K.setVisibility(!arg8.C || (arg8.C()) ? 0 : 8);
                if(arg8.C()) {
                    arg8.f(false);
                }
            }
            else {
                Animator v3 = v0.b;
                if(v3 == null) {
                    goto label_35;
                }
                v3.setTarget(arg8.K);
                if(!arg8.C) {
                    arg8.K.setVisibility(0);
                }
                else if(arg8.C()) {
                    arg8.f(false);
                }
                else {
                    ViewGroup v3_1 = arg8.J;
                    View v4 = arg8.K;
                    v3_1.startViewTransition(v4);
                    v0.b.addListener(new p(this, v3_1, v4, arg8));
                }
                r.b(arg8.K, v0);
                v0.b.start();
            }
        }
        if((arg8.m) && (arg8.G) && (arg8.H)) {
            this.w = true;
        }
        arg8.Q = false;
        arg8.a(arg8.C);
    }

    void b(@NonNull Fragment arg4, @NonNull Context arg5, boolean arg6) {
        Fragment v0 = this.u;
        if(v0 != null) {
            k v0_1 = v0.o();
            if((v0_1 instanceof r)) {
                ((r)v0_1).b(arg4, arg5, true);
            }
        }
        for(Object v1: this.q) {
            f v1_1 = (f)v1;
            if((arg6) && !v1_1.b) {
                continue;
            }
            v1_1.a.b(this, arg4, arg5);
        }
    }

    void b(@NonNull Fragment arg4, @Nullable Bundle arg5, boolean arg6) {
        Fragment v0 = this.u;
        if(v0 != null) {
            k v0_1 = v0.o();
            if((v0_1 instanceof r)) {
                ((r)v0_1).b(arg4, arg5, true);
            }
        }
        for(Object v1: this.q) {
            f v1_1 = (f)v1;
            if((arg6) && !v1_1.b) {
                continue;
            }
            v1_1.a.b(this, arg4, arg5);
        }
    }

    void b(@NonNull Fragment arg4, boolean arg5) {
        Fragment v0 = this.u;
        if(v0 != null) {
            k v0_1 = v0.o();
            if((v0_1 instanceof r)) {
                ((r)v0_1).b(arg4, true);
            }
        }
        for(Object v1: this.q) {
            f v1_1 = (f)v1;
            if((arg5) && !v1_1.b) {
                continue;
            }
            v1_1.a.a(this, arg4);
        }
    }

    public void b(boolean arg3) {
        int v0;
        for(v0 = this.j.size() - 1; v0 >= 0; --v0) {
            Fragment v1 = (Fragment)this.j.get(v0);
            if(v1 != null) {
                v1.e(arg3);
            }
        }
    }

    @Override  // androidx.fragment.app.k
    public boolean b() {
        boolean v0 = this.p();
        this.D();
        return v0;
    }

    public boolean b(Menu arg5) {
        int v1 = 0;
        if(this.r < 1) {
            return 0;
        }
        boolean v0 = false;
        while(v1 < this.j.size()) {
            Fragment v3 = (Fragment)this.j.get(v1);
            if(v3 != null && (v3.d(arg5))) {
                v0 = true;
            }
            ++v1;
        }
        return v0;
    }

    public boolean b(MenuItem arg5) {
        if(this.r < 1) {
            return 0;
        }
        int v0;
        for(v0 = 0; v0 < this.j.size(); ++v0) {
            Fragment v3 = (Fragment)this.j.get(v0);
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
                    if(!((androidx.fragment.app.a)arg5.get(v1)).t) {
                        if(v2 != v1) {
                            this.b(arg5, arg6, v2, v1);
                        }
                        v2 = v1 + 1;
                        if(((Boolean)arg6.get(v1)).booleanValue()) {
                            while(v2 < v0 && (((Boolean)arg6.get(v2)).booleanValue()) && !((androidx.fragment.app.a)arg5.get(v2)).t) {
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
        if(!this.h) {
            if(this.s != null) {
                if(Looper.myLooper() == this.s.e().getLooper()) {
                    if(!arg3) {
                        this.A();
                    }
                    if(this.C == null) {
                        this.C = new ArrayList();
                        this.D = new ArrayList();
                    }
                    this.h = true;
                    try {
                        this.a(null, null);
                    }
                    catch(Throwable v0) {
                        this.h = false;
                        throw v0;
                    }
                    this.h = false;
                    return;
                }
                throw new IllegalStateException("Must be called from main thread of fragment host");
            }
            throw new IllegalStateException("Fragment host has been destroyed");
        }
        throw new IllegalStateException("FragmentManager is already executing transactions");
    }

    @Override  // androidx.fragment.app.k
    public List c() {
        if(this.j.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList v0 = this.j;
        synchronized(v0) {
            return (List)this.j.clone();
        }
    }

    public void c(Fragment arg4) {
        if(r.a) {
            Log.v("FragmentManager", "detach: " + arg4);
        }
        if(!arg4.D) {
            arg4.D = true;
            if(arg4.m) {
                if(r.a) {
                    Log.v("FragmentManager", "remove from detach: " + arg4);
                }
                ArrayList v1 = this.j;
                synchronized(v1) {
                    this.j.remove(arg4);
                }
                if((arg4.G) && (arg4.H)) {
                    this.w = true;
                }
                arg4.m = false;
                return;
            }
        }
    }

    void c(@NonNull Fragment arg4, @Nullable Bundle arg5, boolean arg6) {
        Fragment v0 = this.u;
        if(v0 != null) {
            k v0_1 = v0.o();
            if((v0_1 instanceof r)) {
                ((r)v0_1).c(arg4, arg5, true);
            }
        }
        for(Object v1: this.q) {
            f v1_1 = (f)v1;
            if((arg6) && !v1_1.b) {
                continue;
            }
            v1_1.a.c(this, arg4, arg5);
        }
    }

    void c(@NonNull Fragment arg4, boolean arg5) {
        Fragment v0 = this.u;
        if(v0 != null) {
            k v0_1 = v0.o();
            if((v0_1 instanceof r)) {
                ((r)v0_1).c(arg4, true);
            }
        }
        for(Object v1: this.q) {
            f v1_1 = (f)v1;
            if((arg5) && !v1_1.b) {
                continue;
            }
            v1_1.a.b(this, arg4);
        }
    }

    boolean c(int arg2) {
        return this.r >= arg2;
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

    void d(Fragment arg4) {
        if((arg4.o) && !arg4.r) {
            arg4.b(arg4.i(arg4.d), null, arg4.d);
            View v0 = arg4.K;
            if(v0 != null) {
                arg4.L = v0;
                v0.setSaveFromParentEnabled(false);
                if(arg4.C) {
                    arg4.K.setVisibility(8);
                }
                arg4.a(arg4.K, arg4.d);
                this.a(arg4, arg4.K, arg4.d, false);
                return;
            }
            arg4.L = null;
        }
    }

    void d(@NonNull Fragment arg4, @NonNull Bundle arg5, boolean arg6) {
        Fragment v0 = this.u;
        if(v0 != null) {
            k v0_1 = v0.o();
            if((v0_1 instanceof r)) {
                ((r)v0_1).d(arg4, arg5, true);
            }
        }
        for(Object v1: this.q) {
            f v1_1 = (f)v1;
            if((arg6) && !v1_1.b) {
                continue;
            }
            v1_1.a.d(this, arg4, arg5);
        }
    }

    void d(@NonNull Fragment arg4, boolean arg5) {
        Fragment v0 = this.u;
        if(v0 != null) {
            k v0_1 = v0.o();
            if((v0_1 instanceof r)) {
                ((r)v0_1).d(arg4, true);
            }
        }
        for(Object v1: this.q) {
            f v1_1 = (f)v1;
            if((arg5) && !v1_1.b) {
                continue;
            }
            v1_1.a.c(this, arg4);
        }
    }

    @Override  // androidx.fragment.app.k
    public boolean d() {
        return (this.x) || (this.y);
    }

    private void e(int arg3) {
        try {
            this.h = true;
            this.a(arg3, false);
        }
        catch(Throwable v3) {
            this.h = false;
            throw v3;
        }
        this.h = false;
        this.p();
    }

    public void e(Fragment arg3) {
        if(r.a) {
            Log.v("FragmentManager", "hide: " + arg3);
        }
        if(!arg3.C) {
            arg3.C = true;
            arg3.Q ^= 1;
        }
    }

    void e(@NonNull Fragment arg4, boolean arg5) {
        Fragment v0 = this.u;
        if(v0 != null) {
            k v0_1 = v0.o();
            if((v0_1 instanceof r)) {
                ((r)v0_1).e(arg4, true);
            }
        }
        for(Object v1: this.q) {
            f v1_1 = (f)v1;
            if((arg5) && !v1_1.b) {
                continue;
            }
            v1_1.a.d(this, arg4);
        }
    }

    @Override  // androidx.fragment.app.k
    public boolean e() {
        this.A();
        return this.a(null, -1, 0);
    }

    public void f() {
        this.x = false;
        this.y = false;
        this.e(2);
    }

    void f(Fragment arg3) {
        if(arg3.g >= 0) {
            return;
        }
        int v0 = this.i;
        this.i = v0 + 1;
        arg3.a(v0, this.u);
        if(this.k == null) {
            this.k = new SparseArray();
        }
        this.k.put(arg3.g, arg3);
        if(r.a) {
            Log.v("FragmentManager", "Allocated fragment index " + arg3);
        }
    }

    void f(@NonNull Fragment arg4, boolean arg5) {
        Fragment v0 = this.u;
        if(v0 != null) {
            k v0_1 = v0.o();
            if((v0_1 instanceof r)) {
                ((r)v0_1).f(arg4, true);
            }
        }
        for(Object v1: this.q) {
            f v1_1 = (f)v1;
            if((arg5) && !v1_1.b) {
                continue;
            }
            v1_1.a.e(this, arg4);
        }
    }

    public void g() {
        this.x = false;
        this.y = false;
        this.e(1);
    }

    void g(Fragment arg4) {
        if(arg4.g < 0) {
            return;
        }
        if(r.a) {
            Log.v("FragmentManager", "Freeing fragment index " + arg4);
        }
        this.k.put(arg4.g, null);
        arg4.z();
    }

    void g(@NonNull Fragment arg4, boolean arg5) {
        Fragment v0 = this.u;
        if(v0 != null) {
            k v0_1 = v0.o();
            if((v0_1 instanceof r)) {
                ((r)v0_1).g(arg4, true);
            }
        }
        for(Object v1: this.q) {
            f v1_1 = (f)v1;
            if((arg5) && !v1_1.b) {
                continue;
            }
            v1_1.a.f(this, arg4);
        }
    }

    public void h() {
        this.z = true;
        this.p();
        this.e(0);
        this.s = null;
        this.t = null;
        this.u = null;
    }

    void h(Fragment arg11) {
        if(arg11 == null) {
            return;
        }
        int v0 = this.r;
        if(arg11.n) {
            v0 = arg11.D() ? Math.min(v0, 1) : Math.min(v0, 0);
        }
        this.a(arg11, v0, arg11.q(), arg11.r(), false);
        if(arg11.K != null) {
            Fragment v0_1 = this.p(arg11);
            if(v0_1 != null) {
                View v0_2 = v0_1.K;
                ViewGroup v1 = arg11.J;
                int v0_3 = v1.indexOfChild(v0_2);
                int v4 = v1.indexOfChild(arg11.K);
                if(v4 < v0_3) {
                    v1.removeViewAt(v4);
                    v1.addView(arg11.K, v0_3);
                }
            }
            if((arg11.P) && arg11.J != null) {
                float v0_4 = arg11.R;
                if(v0_4 > 0f) {
                    arg11.K.setAlpha(v0_4);
                }
                arg11.R = 0f;
                arg11.P = false;
                c v0_5 = this.a(arg11, arg11.q(), true, arg11.r());
                if(v0_5 != null) {
                    r.b(arg11.K, v0_5);
                    Animation v1_1 = v0_5.a;
                    if(v1_1 == null) {
                        v0_5.b.setTarget(arg11.K);
                        v0_5.b.start();
                    }
                    else {
                        arg11.K.startAnimation(v1_1);
                    }
                }
            }
        }
        if(arg11.Q) {
            this.b(arg11);
        }
    }

    void h(@NonNull Fragment arg4, boolean arg5) {
        Fragment v0 = this.u;
        if(v0 != null) {
            k v0_1 = v0.o();
            if((v0_1 instanceof r)) {
                ((r)v0_1).h(arg4, true);
            }
        }
        for(Object v1: this.q) {
            f v1_1 = (f)v1;
            if((arg5) && !v1_1.b) {
                continue;
            }
            v1_1.a.g(this, arg4);
        }
    }

    public void i() {
        this.e(1);
    }

    void i(Fragment arg7) {
        this.a(arg7, this.r, 0, 0, false);
    }

    public void j() {
        int v0;
        for(v0 = 0; v0 < this.j.size(); ++v0) {
            Fragment v1 = (Fragment)this.j.get(v0);
            if(v1 != null) {
                v1.T();
            }
        }
    }

    public void j(Fragment arg8) {
        if(arg8.M) {
            if(this.h) {
                this.B = true;
                return;
            }
            arg8.M = false;
            this.a(arg8, this.r, 0, 0, false);
        }
    }

    public void k() {
        this.e(3);
    }

    public void k(Fragment arg4) {
        if(r.a) {
            Log.v("FragmentManager", "remove: " + arg4 + " nesting=" + arg4.s);
        }
        if(!arg4.D || (arg4.D() ^ 1) != 0) {
            ArrayList v0 = this.j;
            synchronized(v0) {
                this.j.remove(arg4);
            }
            if((arg4.G) && (arg4.H)) {
                this.w = true;
            }
            arg4.m = false;
            arg4.n = true;
        }
    }

    Bundle l(Fragment arg4) {
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
        if(arg4.K != null) {
            this.m(arg4);
        }
        if(arg4.e != null) {
            if(v0 == null) {
                v0 = new Bundle();
            }
            v0.putSparseParcelableArray("android:view_state", arg4.e);
        }
        if(!arg4.N) {
            if(v0 == null) {
                v0 = new Bundle();
            }
            v0.putBoolean("android:user_visible_hint", arg4.N);
        }
        return v0;
    }

    public void l() {
        this.x = false;
        this.y = false;
        this.e(4);
    }

    public void m() {
        this.x = false;
        this.y = false;
        this.e(3);
    }

    void m(Fragment arg3) {
        if(arg3.L == null) {
            return;
        }
        SparseArray v0 = this.G;
        if(v0 == null) {
            this.G = new SparseArray();
        }
        else {
            v0.clear();
        }
        arg3.L.saveHierarchyState(this.G);
        if(this.G.size() > 0) {
            arg3.e = this.G;
            this.G = null;
        }
    }

    public void n() {
        this.y = true;
        this.e(2);
    }

    public void n(Fragment arg4) {
        if(arg4 != null && (this.k.get(arg4.g) != arg4 || arg4.u != null && arg4.o() != this)) {
            throw new IllegalArgumentException("Fragment " + arg4 + " is not an active fragment of FragmentManager " + this);
        }
        this.v = arg4;
    }

    void o() {
        if(this.B) {
            this.B = false;
            this.y();
        }
    }

    public void o(Fragment arg3) {
        if(r.a) {
            Log.v("FragmentManager", "show: " + arg3);
        }
        if(arg3.C) {
            arg3.C = false;
            arg3.Q ^= 1;
        }
    }

    @Override  // android.view.LayoutInflater$Factory2
    public View onCreateView(View arg13, String arg14, Context arg15, AttributeSet arg16) {
        Fragment v11;
        r v6 = this;
        AttributeSet v1 = arg16;
        if(!"fragment".equals(arg14)) {
            return null;
        }
        String v2 = v1.getAttributeValue(null, "class");
        TypedArray v4 = arg15.obtainStyledAttributes(v1, g.a);
        int v5 = 0;
        if(v2 == null) {
            v2 = v4.getString(0);
        }
        String v7 = v2;
        int v9 = v4.getResourceId(1, -1);
        String v10 = v4.getString(2);
        v4.recycle();
        if(!Fragment.a(v6.s.c(), v7)) {
            return null;
        }
        if(arg13 != null) {
            v5 = arg13.getId();
        }
        if(v5 == -1 && v9 == -1 && v10 == null) {
            throw new IllegalArgumentException(arg16.getPositionDescription() + ": Must specify unique android:id, android:tag, or have a parent with an id for " + v7);
        }
        Fragment v4_1 = v9 == -1 ? null : this.a(v9);
        if(v4_1 == null && v10 != null) {
            v4_1 = this.a(v10);
        }
        if(v4_1 == null && v5 != -1) {
            v4_1 = this.a(v5);
        }
        if(r.a) {
            Log.v("FragmentManager", "onCreateView: id=0x" + Integer.toHexString(v9) + " fname=" + v7 + " existing=" + v4_1);
        }
        if(v4_1 == null) {
            Fragment v0 = v6.t.a(arg15, v7, null);
            v0.o = true;
            v0.z = v9 == 0 ? v5 : v9;
            v0.A = v5;
            v0.B = v10;
            v0.p = true;
            v0.t = v6;
            j v2_1 = v6.s;
            v0.u = v2_1;
            v0.a(v2_1.c(), v1, v0.d);
            this.a(v0, true);
            v11 = v0;
        }
        else {
            if(v4_1.p) {
                throw new IllegalArgumentException(arg16.getPositionDescription() + ": Duplicate id 0x" + Integer.toHexString(v9) + ", tag " + v10 + ", or parent id 0x" + Integer.toHexString(v5) + " with another fragment for " + v7);
            }
            v4_1.p = true;
            j v0_1 = v6.s;
            v4_1.u = v0_1;
            if(!v4_1.F) {
                v4_1.a(v0_1.c(), v1, v4_1.d);
            }
            v11 = v4_1;
        }
        if(v6.r < 1 && (v11.o)) {
            this.a(v11, 1, 0, 0, false);
        }
        else {
            this.i(v11);
        }
        View v0_2 = v11.K;
        if(v0_2 != null) {
            if(v9 != 0) {
                v0_2.setId(v9);
            }
            if(v11.K.getTag() == null) {
                v11.K.setTag(v10);
            }
            return v11.K;
        }
        throw new IllegalStateException("Fragment " + v7 + " did not create a view.");
    }

    @Override  // android.view.LayoutInflater$Factory
    public View onCreateView(String arg2, Context arg3, AttributeSet arg4) {
        return this.onCreateView(null, arg2, arg3, arg4);
    }

    private Fragment p(Fragment arg5) {
        ViewGroup v0 = arg5.J;
        if(v0 != null && arg5.K != null) {
            int v5;
            for(v5 = this.j.indexOf(arg5) - 1; v5 >= 0; --v5) {
                Fragment v1 = (Fragment)this.j.get(v5);
                if(v1.J == v0 && v1.K != null) {
                    return v1;
                }
            }
        }
        return null;
    }

    public boolean p() {
        this.c(true);
        boolean v1;
        for(v1 = false; this.b(this.C, this.D); v1 = true) {
            this.h = true;
            try {
                this.c(this.C, this.D);
            }
            catch(Throwable v0) {
                this.B();
                throw v0;
            }
            this.B();
        }
        this.o();
        this.z();
        return v1;
    }

    LayoutInflater.Factory2 q() {
        return this;
    }

    @Nullable
    public Fragment r() {
        return this.v;
    }

    public void s() {
        this.I = null;
        int v0 = 0;
        this.x = false;
        this.y = false;
        int v1 = this.j.size();
        while(v0 < v1) {
            Fragment v2 = (Fragment)this.j.get(v0);
            if(v2 != null) {
                v2.G();
            }
            ++v0;
        }
    }

    void t() {
        if(this.p != null) {
            int v0;
            for(v0 = 0; v0 < this.p.size(); ++v0) {
                ((androidx.fragment.app.k.c)this.p.get(v0)).onBackStackChanged();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder v0 = new StringBuilder(0x80);
        v0.append("FragmentManager{");
        v0.append(Integer.toHexString(System.identityHashCode(this)));
        v0.append(" in ");
        Fragment v1 = this.u;
        if(v1 == null) {
            a.g.f.a.a(this.s, v0);
        }
        else {
            a.g.f.a.a(v1, v0);
        }
        v0.append("}}");
        return v0.toString();
    }

    s u() {
        r.a(this.I);
        return this.I;
    }

    Parcelable v() {
        int[] v2_1;
        this.D();
        this.C();
        this.p();
        this.x = true;
        BackStackState[] v1 = null;
        this.I = null;
        if(this.k != null && this.k.size() > 0) {
            int v2 = this.k.size();
            FragmentState[] v3 = new FragmentState[v2];
            int v4 = 0;
            int v5 = 0;
            int v6 = 0;
            while(v5 < v2) {
                Fragment v11 = (Fragment)this.k.valueAt(v5);
                if(v11 != null) {
                    if(v11.g >= 0) {
                        FragmentState v6_1 = new FragmentState(v11);
                        v3[v5] = v6_1;
                        if(v11.c <= 0 || v6_1.k != null) {
                            goto label_71;
                        }
                        v6_1.k = this.l(v11);
                        Fragment v7 = v11.j;
                        if(v7 != null) {
                            if(v7.g < 0) {
                                this.a(new IllegalStateException("Failure saving state: " + v11 + " has target not in fragment manager: " + v11.j));
                                throw null;
                            }
                            if(v6_1.k == null) {
                                v6_1.k = new Bundle();
                            }
                            this.a(v6_1.k, "android:target_state", v11.j);
                            int v7_1 = v11.l;
                            if(v7_1 != 0) {
                                v6_1.k.putInt("android:target_req_state", v7_1);
                                goto label_73;
                                this.a(new IllegalStateException("Failure saving state: " + v11 + " has target not in fragment manager: " + v11.j));
                                throw null;
                            label_71:
                                v6_1.k = v11.d;
                            }
                        }
                    label_73:
                        if(r.a) {
                            Log.v("FragmentManager", "Saved state of " + v11 + ": " + v6_1.k);
                        }
                        v6 = 1;
                        goto label_99;
                    }
                    this.a(new IllegalStateException("Failure saving state: active " + v11 + " has cleared index: " + v11.g));
                    throw null;
                }
            label_99:
                ++v5;
            }
            if(v6 == 0) {
                if(r.a) {
                    Log.v("FragmentManager", "saveAllState: no fragments!");
                }
                return null;
            }
            int v0 = this.j.size();
            if(v0 <= 0) {
                v2_1 = null;
                break;
            }
            v2_1 = new int[v0];
            int v5_1 = 0;
            while(v5_1 < v0) {
                v2_1[v5_1] = ((Fragment)this.j.get(v5_1)).g;
                if(v2_1[v5_1] >= 0) {
                    if(r.a) {
                        Log.v("FragmentManager", "saveAllState: adding fragment #" + v5_1 + ": " + this.j.get(v5_1));
                    }
                    ++v5_1;
                    continue;
                }
                this.a(new IllegalStateException("Failure saving state: active " + this.j.get(v5_1) + " has cleared index: " + v2_1[v5_1]));
                throw null;
            }
            ArrayList v0_1 = this.l;
            if(v0_1 != null) {
                int v0_2 = v0_1.size();
                if(v0_2 > 0) {
                    v1 = new BackStackState[v0_2];
                    while(v4 < v0_2) {
                        v1[v4] = new BackStackState(((androidx.fragment.app.a)this.l.get(v4)));
                        if(r.a) {
                            Log.v("FragmentManager", "saveAllState: adding back stack #" + v4 + ": " + this.l.get(v4));
                        }
                        ++v4;
                    }
                }
            }
            FragmentManagerState v0_3 = new FragmentManagerState();
            v0_3.a = v3;
            v0_3.b = v2_1;
            v0_3.c = v1;
            Fragment v1_1 = this.v;
            if(v1_1 != null) {
                v0_3.d = v1_1.g;
            }
            v0_3.e = this.i;
            this.w();
            return v0_3;
        }
        return null;
    }

    void w() {
        s v7_1;
        ArrayList v5;
        ArrayList v4;
        ArrayList v3;
        if(this.k == null) {
            v3 = null;
            v4 = null;
            v5 = null;
        }
        else {
            int v2 = 0;
            v3 = null;
            v4 = null;
            v5 = null;
            while(v2 < this.k.size()) {
                Fragment v6 = (Fragment)this.k.valueAt(v2);
                if(v6 != null) {
                    if(v6.E) {
                        if(v3 == null) {
                            v3 = new ArrayList();
                        }
                        v3.add(v6);
                        v6.k = v6.j == null ? -1 : v6.j.g;
                        if(r.a) {
                            Log.v("FragmentManager", "retainNonConfig: keeping retained " + v6);
                        }
                    }
                    r v7 = v6.v;
                    if(v7 == null) {
                        v7_1 = v6.w;
                    }
                    else {
                        v7.w();
                        v7_1 = v6.v.I;
                    }
                    if(v4 == null && v7_1 != null) {
                        v4 = new ArrayList(this.k.size());
                        int v8;
                        for(v8 = 0; v8 < v2; ++v8) {
                            v4.add(null);
                        }
                    }
                    if(v4 != null) {
                        v4.add(v7_1);
                    }
                    if(v5 == null && v6.x != null) {
                        v5 = new ArrayList(this.k.size());
                        int v7_2;
                        for(v7_2 = 0; v7_2 < v2; ++v7_2) {
                            v5.add(null);
                        }
                    }
                    if(v5 != null) {
                        v5.add(v6.x);
                    }
                }
                ++v2;
            }
        }
        if(v3 == null && v4 == null && v5 == null) {
            this.I = null;
            return;
        }
        this.I = new s(v3, v4, v5);
    }

    void x() {
        synchronized(this) {
            int v1 = 0;
            int v0_1 = this.H == null || (this.H.isEmpty()) ? 0 : 1;
            if(this.g != null && this.g.size() == 1) {
                v1 = 1;
            }
            if(v0_1 != 0 || v1 != 0) {
                this.s.e().removeCallbacks(this.J);
                this.s.e().post(this.J);
            }
            return;
        }
    }

    void y() {
        if(this.k == null) {
            return;
        }
        int v0;
        for(v0 = 0; v0 < this.k.size(); ++v0) {
            Fragment v1 = (Fragment)this.k.valueAt(v0);
            if(v1 != null) {
                this.j(v1);
            }
        }
    }

    private void z() {
        SparseArray v0 = this.k;
        if(v0 != null) {
            int v0_1;
            for(v0_1 = v0.size() - 1; v0_1 >= 0; --v0_1) {
                if(this.k.valueAt(v0_1) == null) {
                    this.k.delete(this.k.keyAt(v0_1));
                }
            }
        }
    }
}

