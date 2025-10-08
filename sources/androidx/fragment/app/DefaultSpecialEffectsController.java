package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import androidx.collection.ArrayMap;
import androidx.core.os.CancellationSignal;
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewGroupCompat;
import androidx.fragment.app.FragmentAnim;
import androidx.fragment.app.SpecialEffectsController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class DefaultSpecialEffectsController extends SpecialEffectsController {
    DefaultSpecialEffectsController(ViewGroup container) {
        super(container);
    }

    /* access modifiers changed from: package-private */
    public void executeOperations(List<SpecialEffectsController.Operation> operations, boolean isPop) {
        boolean z = isPop;
        SpecialEffectsController.Operation firstOut = null;
        SpecialEffectsController.Operation lastIn = null;
        for (SpecialEffectsController.Operation operation : operations) {
            SpecialEffectsController.Operation.State currentState = SpecialEffectsController.Operation.State.from(operation.getFragment().mView);
            switch (operation.getFinalState()) {
                case GONE:
                case INVISIBLE:
                case REMOVED:
                    if (currentState == SpecialEffectsController.Operation.State.VISIBLE && firstOut == null) {
                        firstOut = operation;
                        break;
                    }
                case VISIBLE:
                    if (currentState == SpecialEffectsController.Operation.State.VISIBLE) {
                        break;
                    } else {
                        lastIn = operation;
                        break;
                    }
            }
        }
        int i = 2;
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.v(FragmentManager.TAG, "Executing operations from " + firstOut + " to " + lastIn);
        }
        List<AnimationInfo> animations = new ArrayList<>();
        List<TransitionInfo> arrayList = new ArrayList<>();
        List<SpecialEffectsController.Operation> list = operations;
        final List<TransitionInfo> arrayList2 = new ArrayList<>(list);
        syncAnimations(operations);
        Iterator<SpecialEffectsController.Operation> it = list.iterator();
        while (true) {
            boolean z2 = true;
            if (it.hasNext()) {
                final SpecialEffectsController.Operation operation2 = it.next();
                CancellationSignal animCancellationSignal = new CancellationSignal();
                operation2.markStartedSpecialEffect(animCancellationSignal);
                animations.add(new AnimationInfo(operation2, animCancellationSignal, z));
                CancellationSignal transitionCancellationSignal = new CancellationSignal();
                operation2.markStartedSpecialEffect(transitionCancellationSignal);
                int i2 = i;
                if (z) {
                    if (operation2 == firstOut) {
                        arrayList.add(new TransitionInfo(operation2, transitionCancellationSignal, z, z2));
                        operation2.addCompletionListener(new Runnable() {
                            public void run() {
                                if (arrayList2.contains(operation2)) {
                                    arrayList2.remove(operation2);
                                    DefaultSpecialEffectsController.this.applyContainerChanges(operation2);
                                }
                            }
                        });
                        i = i2;
                    }
                } else if (operation2 == lastIn) {
                    arrayList.add(new TransitionInfo(operation2, transitionCancellationSignal, z, z2));
                    operation2.addCompletionListener(new Runnable() {
                        public void run() {
                            if (arrayList2.contains(operation2)) {
                                arrayList2.remove(operation2);
                                DefaultSpecialEffectsController.this.applyContainerChanges(operation2);
                            }
                        }
                    });
                    i = i2;
                }
                z2 = false;
                arrayList.add(new TransitionInfo(operation2, transitionCancellationSignal, z, z2));
                operation2.addCompletionListener(new Runnable() {
                    public void run() {
                        if (arrayList2.contains(operation2)) {
                            arrayList2.remove(operation2);
                            DefaultSpecialEffectsController.this.applyContainerChanges(operation2);
                        }
                    }
                });
                i = i2;
            } else {
                int i3 = i;
                List<TransitionInfo> list2 = arrayList2;
                SpecialEffectsController.Operation firstOut2 = firstOut;
                List<TransitionInfo> transitions = arrayList;
                List<TransitionInfo> transitions2 = list2;
                Map<SpecialEffectsController.Operation, Boolean> startedTransitions = startTransitions(transitions, transitions2, z, firstOut2, lastIn);
                startAnimations(animations, transitions2, startedTransitions.containsValue(true), startedTransitions);
                Iterator<TransitionInfo> it2 = transitions2.iterator();
                while (it2.hasNext()) {
                    applyContainerChanges((SpecialEffectsController.Operation) it2.next());
                }
                transitions2.clear();
                if (FragmentManager.isLoggingEnabled(i3)) {
                    Log.v(FragmentManager.TAG, "Completed executing operations from " + firstOut2 + " to " + lastIn);
                    return;
                }
                return;
            }
        }
    }

    private void syncAnimations(List<SpecialEffectsController.Operation> operations) {
        Fragment lastOpFragment = operations.get(operations.size() - 1).getFragment();
        for (SpecialEffectsController.Operation operation : operations) {
            operation.getFragment().mAnimationInfo.mEnterAnim = lastOpFragment.mAnimationInfo.mEnterAnim;
            operation.getFragment().mAnimationInfo.mExitAnim = lastOpFragment.mAnimationInfo.mExitAnim;
            operation.getFragment().mAnimationInfo.mPopEnterAnim = lastOpFragment.mAnimationInfo.mPopEnterAnim;
            operation.getFragment().mAnimationInfo.mPopExitAnim = lastOpFragment.mAnimationInfo.mPopExitAnim;
        }
    }

    private void startAnimations(List<AnimationInfo> animationInfos, List<SpecialEffectsController.Operation> awaitingContainerChanges, boolean startedAnyTransition, Map<SpecialEffectsController.Operation, Boolean> startedTransitions) {
        int i;
        int i2;
        final AnimationInfo animationInfo;
        final View viewToAnimate;
        SpecialEffectsController.Operation operation;
        ViewGroup container = getContainer();
        Context context = container.getContext();
        ArrayList arrayList = new ArrayList();
        Iterator<AnimationInfo> it = animationInfos.iterator();
        boolean startedAnyAnimator = false;
        while (true) {
            i = 2;
            if (!it.hasNext()) {
                break;
            }
            AnimationInfo animationInfo2 = it.next();
            if (animationInfo2.isVisibilityUnchanged()) {
                animationInfo2.completeSpecialEffect();
                Map<SpecialEffectsController.Operation, Boolean> map = startedTransitions;
            } else {
                FragmentAnim.AnimationOrAnimator anim = animationInfo2.getAnimation(context);
                if (anim == null) {
                    animationInfo2.completeSpecialEffect();
                    Map<SpecialEffectsController.Operation, Boolean> map2 = startedTransitions;
                } else {
                    final Animator animator = anim.animator;
                    if (animator == null) {
                        arrayList.add(animationInfo2);
                        Map<SpecialEffectsController.Operation, Boolean> map3 = startedTransitions;
                    } else {
                        final SpecialEffectsController.Operation operation2 = animationInfo2.getOperation();
                        Fragment fragment = operation2.getFragment();
                        if (Boolean.TRUE.equals(startedTransitions.get(operation2))) {
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Log.v(FragmentManager.TAG, "Ignoring Animator set on " + fragment + " as this Fragment was involved in a Transition.");
                            }
                            animationInfo2.completeSpecialEffect();
                        } else {
                            final boolean isHideOperation = operation2.getFinalState() == SpecialEffectsController.Operation.State.GONE;
                            if (isHideOperation) {
                                awaitingContainerChanges.remove(operation2);
                            } else {
                                List<SpecialEffectsController.Operation> list = awaitingContainerChanges;
                            }
                            View viewToAnimate2 = fragment.mView;
                            container.startViewTransition(viewToAnimate2);
                            final ViewGroup container2 = container;
                            final View viewToAnimate3 = viewToAnimate2;
                            final AnimationInfo animationInfo3 = animationInfo2;
                            animator.addListener(new AnimatorListenerAdapter() {
                                public void onAnimationEnd(Animator anim) {
                                    container2.endViewTransition(viewToAnimate3);
                                    if (isHideOperation) {
                                        operation2.getFinalState().applyState(viewToAnimate3);
                                    }
                                    animationInfo3.completeSpecialEffect();
                                    if (FragmentManager.isLoggingEnabled(2)) {
                                        Log.v(FragmentManager.TAG, "Animator from operation " + operation2 + " has ended.");
                                    }
                                }
                            });
                            animator.setTarget(viewToAnimate3);
                            animator.start();
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Log.v(FragmentManager.TAG, "Animator from operation " + operation2 + " has started.");
                            }
                            animationInfo3.getSignal().setOnCancelListener(new CancellationSignal.OnCancelListener() {
                                public void onCancel() {
                                    animator.end();
                                    if (FragmentManager.isLoggingEnabled(2)) {
                                        Log.v(FragmentManager.TAG, "Animator from operation " + operation2 + " has been canceled.");
                                    }
                                }
                            });
                            container = container2;
                            startedAnyAnimator = true;
                        }
                    }
                }
            }
        }
        ViewGroup container3 = container;
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            AnimationInfo animationInfo4 = (AnimationInfo) it2.next();
            SpecialEffectsController.Operation operation3 = animationInfo4.getOperation();
            Fragment fragment2 = operation3.getFragment();
            if (startedAnyTransition) {
                if (FragmentManager.isLoggingEnabled(i)) {
                    Log.v(FragmentManager.TAG, "Ignoring Animation set on " + fragment2 + " as Animations cannot run alongside Transitions.");
                }
                animationInfo4.completeSpecialEffect();
            } else if (startedAnyAnimator) {
                if (FragmentManager.isLoggingEnabled(i)) {
                    Log.v(FragmentManager.TAG, "Ignoring Animation set on " + fragment2 + " as Animations cannot run alongside Animators.");
                }
                animationInfo4.completeSpecialEffect();
            } else {
                View viewToAnimate4 = fragment2.mView;
                Animation anim2 = (Animation) Preconditions.checkNotNull(((FragmentAnim.AnimationOrAnimator) Preconditions.checkNotNull(animationInfo4.getAnimation(context))).animation);
                if (operation3.getFinalState() != SpecialEffectsController.Operation.State.REMOVED) {
                    viewToAnimate4.startAnimation(anim2);
                    animationInfo4.completeSpecialEffect();
                    AnimationInfo animationInfo5 = animationInfo4;
                    viewToAnimate = viewToAnimate4;
                    operation = operation3;
                    animationInfo = animationInfo5;
                    i2 = i;
                } else {
                    container3.startViewTransition(viewToAnimate4);
                    Animation endViewTransitionAnimation = new FragmentAnim.EndViewTransitionAnimation(anim2, container3, viewToAnimate4);
                    final ViewGroup container4 = container3;
                    final SpecialEffectsController.Operation operation4 = operation3;
                    animationInfo = animationInfo4;
                    viewToAnimate = viewToAnimate4;
                    i2 = i;
                    Animation animation = endViewTransitionAnimation;
                    AnonymousClass4 r1 = new Animation.AnimationListener() {
                        public void onAnimationStart(Animation animation) {
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Log.v(FragmentManager.TAG, "Animation from operation " + operation4 + " has reached onAnimationStart.");
                            }
                        }

                        public void onAnimationEnd(Animation animation) {
                            container4.post(new Runnable() {
                                public void run() {
                                    container4.endViewTransition(viewToAnimate);
                                    animationInfo.completeSpecialEffect();
                                }
                            });
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Log.v(FragmentManager.TAG, "Animation from operation " + operation4 + " has ended.");
                            }
                        }

                        public void onAnimationRepeat(Animation animation) {
                        }
                    };
                    operation = operation4;
                    container3 = container4;
                    animation.setAnimationListener(r1);
                    viewToAnimate.startAnimation(animation);
                    if (FragmentManager.isLoggingEnabled(i2)) {
                        Log.v(FragmentManager.TAG, "Animation from operation " + operation + " has started.");
                    }
                }
                CancellationSignal signal = animationInfo.getSignal();
                final ViewGroup container5 = container3;
                final View viewToAnimate5 = viewToAnimate;
                final AnimationInfo animationInfo6 = animationInfo;
                final SpecialEffectsController.Operation operation5 = operation;
                AnonymousClass5 r12 = new CancellationSignal.OnCancelListener() {
                    public void onCancel() {
                        viewToAnimate5.clearAnimation();
                        container5.endViewTransition(viewToAnimate5);
                        animationInfo6.completeSpecialEffect();
                        if (FragmentManager.isLoggingEnabled(2)) {
                            Log.v(FragmentManager.TAG, "Animation from operation " + operation5 + " has been cancelled.");
                        }
                    }
                };
                SpecialEffectsController.Operation operation6 = operation5;
                AnimationInfo animationInfo7 = animationInfo6;
                View view = viewToAnimate5;
                container3 = container5;
                signal.setOnCancelListener(r12);
                i = i2;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:161:0x059f, code lost:
        if (r11 == r39) goto L_0x05a4;
     */
    /* JADX WARNING: Removed duplicated region for block: B:171:0x05c7  */
    /* JADX WARNING: Removed duplicated region for block: B:176:0x0606  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.Map<androidx.fragment.app.SpecialEffectsController.Operation, java.lang.Boolean> startTransitions(java.util.List<androidx.fragment.app.DefaultSpecialEffectsController.TransitionInfo> r35, java.util.List<androidx.fragment.app.SpecialEffectsController.Operation> r36, boolean r37, androidx.fragment.app.SpecialEffectsController.Operation r38, androidx.fragment.app.SpecialEffectsController.Operation r39) {
        /*
            r34 = this;
            r1 = r34
            r4 = r37
            r3 = r38
            r2 = r39
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            r6 = r0
            r0 = 0
            java.util.Iterator r5 = r35.iterator()
            r7 = r0
        L_0x0014:
            boolean r0 = r5.hasNext()
            if (r0 == 0) goto L_0x006a
            java.lang.Object r0 = r5.next()
            androidx.fragment.app.DefaultSpecialEffectsController$TransitionInfo r0 = (androidx.fragment.app.DefaultSpecialEffectsController.TransitionInfo) r0
            boolean r8 = r0.isVisibilityUnchanged()
            if (r8 == 0) goto L_0x0027
            goto L_0x0014
        L_0x0027:
            androidx.fragment.app.FragmentTransitionImpl r8 = r0.getHandlingImpl()
            if (r7 != 0) goto L_0x002f
            r7 = r8
            goto L_0x0069
        L_0x002f:
            if (r8 == 0) goto L_0x0069
            if (r7 != r8) goto L_0x0034
            goto L_0x0069
        L_0x0034:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "Mixing framework transitions and AndroidX transitions is not allowed. Fragment "
            java.lang.StringBuilder r9 = r9.append(r10)
            androidx.fragment.app.SpecialEffectsController$Operation r10 = r0.getOperation()
            androidx.fragment.app.Fragment r10 = r10.getFragment()
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r10 = " returned Transition "
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.Object r10 = r0.getTransition()
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r10 = " which uses a different Transition  type than other Fragments."
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r9 = r9.toString()
            r5.<init>(r9)
            throw r5
        L_0x0069:
            goto L_0x0014
        L_0x006a:
            r15 = 0
            if (r7 != 0) goto L_0x008d
            java.util.Iterator r0 = r35.iterator()
        L_0x0071:
            boolean r5 = r0.hasNext()
            if (r5 == 0) goto L_0x008c
            java.lang.Object r5 = r0.next()
            androidx.fragment.app.DefaultSpecialEffectsController$TransitionInfo r5 = (androidx.fragment.app.DefaultSpecialEffectsController.TransitionInfo) r5
            androidx.fragment.app.SpecialEffectsController$Operation r8 = r5.getOperation()
            java.lang.Boolean r9 = java.lang.Boolean.valueOf(r15)
            r6.put(r8, r9)
            r5.completeSpecialEffect()
            goto L_0x0071
        L_0x008c:
            return r6
        L_0x008d:
            android.view.View r0 = new android.view.View
            android.view.ViewGroup r5 = r1.getContainer()
            android.content.Context r5 = r5.getContext()
            r0.<init>(r5)
            r8 = r0
            r0 = 0
            r5 = 0
            r9 = 0
            android.graphics.Rect r10 = new android.graphics.Rect
            r10.<init>()
            java.util.ArrayList r11 = new java.util.ArrayList
            r11.<init>()
            java.util.ArrayList r14 = new java.util.ArrayList
            r14.<init>()
            androidx.collection.ArrayMap r12 = new androidx.collection.ArrayMap
            r12.<init>()
            java.util.Iterator r16 = r35.iterator()
            r17 = r9
            r9 = r5
        L_0x00b9:
            boolean r5 = r16.hasNext()
            r18 = 2
            java.lang.String r15 = "FragmentManager"
            if (r5 == 0) goto L_0x040a
            java.lang.Object r5 = r16.next()
            r20 = r5
            androidx.fragment.app.DefaultSpecialEffectsController$TransitionInfo r20 = (androidx.fragment.app.DefaultSpecialEffectsController.TransitionInfo) r20
            boolean r21 = r20.hasSharedElementTransition()
            if (r21 == 0) goto L_0x03f1
            if (r3 == 0) goto L_0x03f1
            if (r2 == 0) goto L_0x03f1
            java.lang.Object r5 = r20.getSharedElementTransition()
            java.lang.Object r5 = r7.cloneTransition(r5)
            java.lang.Object r0 = r7.wrapTransitionInSet(r5)
            androidx.fragment.app.Fragment r5 = r2.getFragment()
            java.util.ArrayList r5 = r5.getSharedElementSourceNames()
            androidx.fragment.app.Fragment r22 = r3.getFragment()
            r23 = r9
            java.util.ArrayList r9 = r22.getSharedElementSourceNames()
            androidx.fragment.app.Fragment r22 = r3.getFragment()
            r24 = 1
            java.util.ArrayList r13 = r22.getSharedElementTargetNames()
            r22 = 0
            r25 = r0
            r0 = r22
        L_0x0104:
            r22 = r6
            int r6 = r13.size()
            if (r0 >= r6) goto L_0x0129
            java.lang.Object r6 = r13.get(r0)
            int r6 = r5.indexOf(r6)
            r26 = r13
            r13 = -1
            if (r6 == r13) goto L_0x0122
            java.lang.Object r13 = r9.get(r0)
            java.lang.String r13 = (java.lang.String) r13
            r5.set(r6, r13)
        L_0x0122:
            int r0 = r0 + 1
            r6 = r22
            r13 = r26
            goto L_0x0104
        L_0x0129:
            r26 = r13
            androidx.fragment.app.Fragment r0 = r2.getFragment()
            java.util.ArrayList r6 = r0.getSharedElementTargetNames()
            if (r4 != 0) goto L_0x014b
            androidx.fragment.app.Fragment r0 = r3.getFragment()
            androidx.core.app.SharedElementCallback r0 = r0.getExitTransitionCallback()
            androidx.fragment.app.Fragment r13 = r2.getFragment()
            androidx.core.app.SharedElementCallback r13 = r13.getEnterTransitionCallback()
            r33 = r13
            r13 = r0
            r0 = r33
            goto L_0x0160
        L_0x014b:
            androidx.fragment.app.Fragment r0 = r3.getFragment()
            androidx.core.app.SharedElementCallback r0 = r0.getEnterTransitionCallback()
            androidx.fragment.app.Fragment r13 = r2.getFragment()
            androidx.core.app.SharedElementCallback r13 = r13.getExitTransitionCallback()
            r33 = r13
            r13 = r0
            r0 = r33
        L_0x0160:
            r27 = r9
            int r9 = r5.size()
            r28 = 0
            r29 = r8
            r8 = r28
        L_0x016c:
            if (r8 >= r9) goto L_0x018a
            java.lang.Object r28 = r5.get(r8)
            r30 = r9
            r9 = r28
            java.lang.String r9 = (java.lang.String) r9
            java.lang.Object r28 = r6.get(r8)
            r31 = r8
            r8 = r28
            java.lang.String r8 = (java.lang.String) r8
            r12.put(r9, r8)
            int r8 = r31 + 1
            r9 = r30
            goto L_0x016c
        L_0x018a:
            r31 = r8
            r30 = r9
            boolean r8 = androidx.fragment.app.FragmentManager.isLoggingEnabled(r18)
            if (r8 == 0) goto L_0x01f8
            java.lang.String r8 = ">>> entering view names <<<"
            android.util.Log.v(r15, r8)
            java.util.Iterator r8 = r6.iterator()
        L_0x019d:
            boolean r9 = r8.hasNext()
            r28 = r8
            java.lang.String r8 = "Name: "
            if (r9 == 0) goto L_0x01c8
            java.lang.Object r9 = r28.next()
            java.lang.String r9 = (java.lang.String) r9
            r31 = r10
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.StringBuilder r8 = r10.append(r8)
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r8 = r8.toString()
            android.util.Log.v(r15, r8)
            r8 = r28
            r10 = r31
            goto L_0x019d
        L_0x01c8:
            r31 = r10
            java.lang.String r9 = ">>> exiting view names <<<"
            android.util.Log.v(r15, r9)
            java.util.Iterator r9 = r5.iterator()
        L_0x01d3:
            boolean r10 = r9.hasNext()
            if (r10 == 0) goto L_0x01fa
            java.lang.Object r10 = r9.next()
            java.lang.String r10 = (java.lang.String) r10
            r28 = r9
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.StringBuilder r9 = r9.append(r8)
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r9 = r9.toString()
            android.util.Log.v(r15, r9)
            r9 = r28
            goto L_0x01d3
        L_0x01f8:
            r31 = r10
        L_0x01fa:
            androidx.collection.ArrayMap r8 = new androidx.collection.ArrayMap
            r8.<init>()
            androidx.fragment.app.Fragment r9 = r3.getFragment()
            android.view.View r9 = r9.mView
            r1.findNamedViews(r8, r9)
            r8.retainAll(r5)
            if (r13 == 0) goto L_0x026e
            boolean r9 = androidx.fragment.app.FragmentManager.isLoggingEnabled(r18)
            if (r9 == 0) goto L_0x0229
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "Executing exit callback for operation "
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.StringBuilder r9 = r9.append(r3)
            java.lang.String r9 = r9.toString()
            android.util.Log.v(r15, r9)
        L_0x0229:
            r13.onMapSharedElements(r5, r8)
            int r9 = r5.size()
            int r9 = r9 + -1
        L_0x0232:
            if (r9 < 0) goto L_0x026b
            java.lang.Object r10 = r5.get(r9)
            java.lang.String r10 = (java.lang.String) r10
            java.lang.Object r28 = r8.get(r10)
            android.view.View r28 = (android.view.View) r28
            if (r28 != 0) goto L_0x0248
            r12.remove(r10)
            r32 = r5
            goto L_0x0264
        L_0x0248:
            java.lang.String r3 = androidx.core.view.ViewCompat.getTransitionName(r28)
            boolean r3 = r10.equals(r3)
            if (r3 != 0) goto L_0x0262
            java.lang.Object r3 = r12.remove(r10)
            java.lang.String r3 = (java.lang.String) r3
            r32 = r5
            java.lang.String r5 = androidx.core.view.ViewCompat.getTransitionName(r28)
            r12.put(r5, r3)
            goto L_0x0264
        L_0x0262:
            r32 = r5
        L_0x0264:
            int r9 = r9 + -1
            r3 = r38
            r5 = r32
            goto L_0x0232
        L_0x026b:
            r32 = r5
            goto L_0x0277
        L_0x026e:
            r32 = r5
            java.util.Set r3 = r8.keySet()
            r12.retainAll(r3)
        L_0x0277:
            androidx.collection.ArrayMap r5 = new androidx.collection.ArrayMap
            r5.<init>()
            androidx.fragment.app.Fragment r3 = r2.getFragment()
            android.view.View r3 = r3.mView
            r1.findNamedViews(r5, r3)
            r5.retainAll(r6)
            java.util.Collection r3 = r12.values()
            r5.retainAll(r3)
            if (r0 == 0) goto L_0x02fa
            boolean r3 = androidx.fragment.app.FragmentManager.isLoggingEnabled(r18)
            if (r3 == 0) goto L_0x02ad
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r9 = "Executing enter callback for operation "
            java.lang.StringBuilder r3 = r3.append(r9)
            java.lang.StringBuilder r3 = r3.append(r2)
            java.lang.String r3 = r3.toString()
            android.util.Log.v(r15, r3)
        L_0x02ad:
            r0.onMapSharedElements(r6, r5)
            int r3 = r6.size()
            int r3 = r3 + -1
        L_0x02b6:
            if (r3 < 0) goto L_0x02f7
            java.lang.Object r9 = r6.get(r3)
            java.lang.String r9 = (java.lang.String) r9
            java.lang.Object r10 = r5.get(r9)
            android.view.View r10 = (android.view.View) r10
            if (r10 != 0) goto L_0x02d2
            java.lang.String r15 = androidx.fragment.app.FragmentTransition.findKeyForValue(r12, r9)
            if (r15 == 0) goto L_0x02cf
            r12.remove(r15)
        L_0x02cf:
            r28 = r0
            goto L_0x02f2
        L_0x02d2:
            java.lang.String r15 = androidx.core.view.ViewCompat.getTransitionName(r10)
            boolean r15 = r9.equals(r15)
            if (r15 != 0) goto L_0x02f0
            java.lang.String r15 = androidx.fragment.app.FragmentTransition.findKeyForValue(r12, r9)
            if (r15 == 0) goto L_0x02ed
            r28 = r0
            java.lang.String r0 = androidx.core.view.ViewCompat.getTransitionName(r10)
            r12.put(r15, r0)
            goto L_0x02f2
        L_0x02ed:
            r28 = r0
            goto L_0x02f2
        L_0x02f0:
            r28 = r0
        L_0x02f2:
            int r3 = r3 + -1
            r0 = r28
            goto L_0x02b6
        L_0x02f7:
            r28 = r0
            goto L_0x02ff
        L_0x02fa:
            r28 = r0
            androidx.fragment.app.FragmentTransition.retainValues(r12, r5)
        L_0x02ff:
            java.util.Set r0 = r12.keySet()
            r1.retainMatchingViews(r8, r0)
            java.util.Collection r0 = r12.values()
            r1.retainMatchingViews(r5, r0)
            boolean r0 = r12.isEmpty()
            if (r0 == 0) goto L_0x032b
            r0 = 0
            r11.clear()
            r14.clear()
            r3 = r38
            r25 = r11
            r26 = r12
            r8 = r14
            r11 = r22
            r9 = r23
            r4 = r29
            r5 = r31
            goto L_0x03fd
        L_0x032b:
            androidx.fragment.app.Fragment r0 = r2.getFragment()
            androidx.fragment.app.Fragment r3 = r38.getFragment()
            r9 = r24
            androidx.fragment.app.FragmentTransition.callSharedElementStartEnd(r0, r3, r4, r8, r9)
            android.view.ViewGroup r10 = r1.getContainer()
            androidx.fragment.app.DefaultSpecialEffectsController$6 r0 = new androidx.fragment.app.DefaultSpecialEffectsController$6
            r3 = r38
            r15 = r25
            r0.<init>(r2, r3, r4, r5)
            androidx.core.view.OneShotPreDrawListener.add(r10, r0)
            java.util.Collection r0 = r8.values()
            r11.addAll(r0)
            boolean r0 = r32.isEmpty()
            if (r0 != 0) goto L_0x036c
            r0 = r32
            r4 = 0
            java.lang.Object r10 = r0.get(r4)
            r4 = r10
            java.lang.String r4 = (java.lang.String) r4
            java.lang.Object r10 = r8.get(r4)
            android.view.View r10 = (android.view.View) r10
            r7.setEpicenter((java.lang.Object) r15, (android.view.View) r10)
            r23 = r10
            goto L_0x036e
        L_0x036c:
            r0 = r32
        L_0x036e:
            java.util.Collection r4 = r5.values()
            r14.addAll(r4)
            boolean r4 = r6.isEmpty()
            if (r4 != 0) goto L_0x03b1
            r4 = 0
            java.lang.Object r10 = r6.get(r4)
            r4 = r10
            java.lang.String r4 = (java.lang.String) r4
            java.lang.Object r10 = r5.get(r4)
            android.view.View r10 = (android.view.View) r10
            if (r10 == 0) goto L_0x03a8
            r17 = 1
            r18 = r7
            android.view.ViewGroup r9 = r1.getContainer()
            r32 = r0
            androidx.fragment.app.DefaultSpecialEffectsController$7 r0 = new androidx.fragment.app.DefaultSpecialEffectsController$7
            r25 = r18
            r18 = r5
            r5 = r25
            r25 = r4
            r4 = r31
            r0.<init>(r5, r10, r4)
            androidx.core.view.OneShotPreDrawListener.add(r9, r0)
            goto L_0x03b7
        L_0x03a8:
            r32 = r0
            r25 = r4
            r18 = r5
            r4 = r31
            goto L_0x03b7
        L_0x03b1:
            r32 = r0
            r18 = r5
            r4 = r31
        L_0x03b7:
            r0 = r29
            r7.setSharedElementTargets(r15, r0, r11)
            r9 = r11
            r11 = 0
            r5 = r12
            r12 = 0
            r10 = r9
            r9 = 0
            r25 = r10
            r10 = 0
            r29 = r13
            r13 = r15
            r24 = r4
            r4 = r0
            r0 = r26
            r26 = r5
            r5 = r24
            r24 = r15
            r15 = r8
            r8 = r24
            r24 = 1
            r7.scheduleRemoveTargets(r8, r9, r10, r11, r12, r13, r14)
            r9 = r8
            r8 = r14
            java.lang.Boolean r10 = java.lang.Boolean.valueOf(r24)
            r11 = r22
            r11.put(r3, r10)
            java.lang.Boolean r10 = java.lang.Boolean.valueOf(r24)
            r11.put(r2, r10)
            r0 = r9
            r9 = r23
            goto L_0x03fd
        L_0x03f1:
            r4 = r8
            r23 = r9
            r5 = r10
            r25 = r11
            r26 = r12
            r8 = r14
            r11 = r6
            r9 = r23
        L_0x03fd:
            r10 = r5
            r14 = r8
            r6 = r11
            r11 = r25
            r12 = r26
            r15 = 0
            r8 = r4
            r4 = r37
            goto L_0x00b9
        L_0x040a:
            r4 = r11
            r11 = r6
            r6 = r4
            r4 = r8
            r23 = r9
            r5 = r10
            r26 = r12
            r8 = r14
            r24 = 1
            java.util.ArrayList r9 = new java.util.ArrayList
            r9.<init>()
            r10 = 0
            r12 = 0
            java.util.Iterator r16 = r35.iterator()
        L_0x0421:
            boolean r13 = r16.hasNext()
            if (r13 == 0) goto L_0x0561
            java.lang.Object r13 = r16.next()
            r20 = r13
            androidx.fragment.app.DefaultSpecialEffectsController$TransitionInfo r20 = (androidx.fragment.app.DefaultSpecialEffectsController.TransitionInfo) r20
            boolean r13 = r20.isVisibilityUnchanged()
            if (r13 == 0) goto L_0x0446
            androidx.fragment.app.SpecialEffectsController$Operation r13 = r20.getOperation()
            r19 = 0
            java.lang.Boolean r14 = java.lang.Boolean.valueOf(r19)
            r11.put(r13, r14)
            r20.completeSpecialEffect()
            goto L_0x0421
        L_0x0446:
            java.lang.Object r13 = r20.getTransition()
            java.lang.Object r13 = r7.cloneTransition(r13)
            androidx.fragment.app.SpecialEffectsController$Operation r14 = r20.getOperation()
            if (r0 == 0) goto L_0x045b
            if (r14 == r3) goto L_0x0458
            if (r14 != r2) goto L_0x045b
        L_0x0458:
            r21 = r24
            goto L_0x045d
        L_0x045b:
            r21 = 0
        L_0x045d:
            if (r13 != 0) goto L_0x0483
            if (r21 != 0) goto L_0x0470
            r22 = r9
            r19 = 0
            java.lang.Boolean r9 = java.lang.Boolean.valueOf(r19)
            r11.put(r14, r9)
            r20.completeSpecialEffect()
            goto L_0x0472
        L_0x0470:
            r22 = r9
        L_0x0472:
            r13 = r36
            r31 = r4
            r25 = r6
            r30 = r8
            r4 = r11
            r6 = r22
            r3 = r23
            r22 = r15
            goto L_0x054f
        L_0x0483:
            r22 = r9
            java.util.ArrayList r9 = new java.util.ArrayList
            r9.<init>()
            r25 = r10
            androidx.fragment.app.Fragment r10 = r14.getFragment()
            android.view.View r10 = r10.mView
            r1.captureTransitioningViews(r9, r10)
            if (r21 == 0) goto L_0x04a1
            if (r14 != r3) goto L_0x049e
            r9.removeAll(r6)
            goto L_0x04a1
        L_0x049e:
            r9.removeAll(r8)
        L_0x04a1:
            boolean r10 = r9.isEmpty()
            if (r10 == 0) goto L_0x04c1
            r7.addTarget(r13, r4)
            r2 = r25
            r25 = r6
            r6 = r22
            r22 = r15
            r15 = r2
            r31 = r4
            r30 = r8
            r10 = r9
            r4 = r11
            r2 = r12
            r8 = r13
            r9 = r14
            r3 = r23
            r13 = r36
            goto L_0x0520
        L_0x04c1:
            r7.addTargets(r13, r9)
            r10 = r8
            r8 = r13
            r13 = 0
            r27 = r14
            r14 = 0
            r28 = r11
            r11 = 0
            r29 = r12
            r12 = 0
            r30 = r10
            r10 = r9
            r9 = r8
            r2 = r25
            r25 = r6
            r6 = r22
            r22 = r15
            r15 = r2
            r31 = r4
            r3 = r23
            r4 = r28
            r2 = r29
            r7.scheduleRemoveTargets(r8, r9, r10, r11, r12, r13, r14)
            androidx.fragment.app.SpecialEffectsController$Operation$State r9 = r27.getFinalState()
            androidx.fragment.app.SpecialEffectsController$Operation$State r11 = androidx.fragment.app.SpecialEffectsController.Operation.State.GONE
            if (r9 != r11) goto L_0x051c
            r13 = r36
            r9 = r27
            r13.remove(r9)
            java.util.ArrayList r11 = new java.util.ArrayList
            r11.<init>(r10)
            androidx.fragment.app.Fragment r12 = r9.getFragment()
            android.view.View r12 = r12.mView
            r11.remove(r12)
            androidx.fragment.app.Fragment r12 = r9.getFragment()
            android.view.View r12 = r12.mView
            r7.scheduleHideFragmentView(r8, r12, r11)
            android.view.ViewGroup r12 = r1.getContainer()
            androidx.fragment.app.DefaultSpecialEffectsController$8 r14 = new androidx.fragment.app.DefaultSpecialEffectsController$8
            r14.<init>(r10)
            androidx.core.view.OneShotPreDrawListener.add(r12, r14)
            goto L_0x0520
        L_0x051c:
            r13 = r36
            r9 = r27
        L_0x0520:
            androidx.fragment.app.SpecialEffectsController$Operation$State r11 = r9.getFinalState()
            androidx.fragment.app.SpecialEffectsController$Operation$State r12 = androidx.fragment.app.SpecialEffectsController.Operation.State.VISIBLE
            if (r11 != r12) goto L_0x0531
            r6.addAll(r10)
            if (r17 == 0) goto L_0x0534
            r7.setEpicenter((java.lang.Object) r8, (android.graphics.Rect) r5)
            goto L_0x0534
        L_0x0531:
            r7.setEpicenter((java.lang.Object) r8, (android.view.View) r3)
        L_0x0534:
            java.lang.Boolean r11 = java.lang.Boolean.valueOf(r24)
            r4.put(r9, r11)
            boolean r11 = r20.isOverlapAllowed()
            r12 = 0
            if (r11 == 0) goto L_0x0549
            java.lang.Object r11 = r7.mergeTransitionsTogether(r15, r8, r12)
            r12 = r2
            r10 = r11
            goto L_0x054f
        L_0x0549:
            java.lang.Object r2 = r7.mergeTransitionsTogether(r2, r8, r12)
            r12 = r2
            r10 = r15
        L_0x054f:
            r2 = r39
            r23 = r3
            r11 = r4
            r9 = r6
            r15 = r22
            r6 = r25
            r8 = r30
            r4 = r31
            r3 = r38
            goto L_0x0421
        L_0x0561:
            r13 = r36
            r31 = r4
            r25 = r6
            r30 = r8
            r6 = r9
            r4 = r11
            r2 = r12
            r22 = r15
            r3 = r23
            r15 = r10
            java.lang.Object r14 = r7.mergeTransitionsInSequence(r15, r2, r0)
            if (r14 != 0) goto L_0x0578
            return r4
        L_0x0578:
            java.util.Iterator r8 = r35.iterator()
        L_0x057c:
            boolean r9 = r8.hasNext()
            if (r9 == 0) goto L_0x0630
            java.lang.Object r9 = r8.next()
            androidx.fragment.app.DefaultSpecialEffectsController$TransitionInfo r9 = (androidx.fragment.app.DefaultSpecialEffectsController.TransitionInfo) r9
            boolean r10 = r9.isVisibilityUnchanged()
            if (r10 == 0) goto L_0x058f
            goto L_0x057c
        L_0x058f:
            java.lang.Object r10 = r9.getTransition()
            androidx.fragment.app.SpecialEffectsController$Operation r11 = r9.getOperation()
            if (r0 == 0) goto L_0x05a7
            r15 = r38
            if (r11 == r15) goto L_0x05a2
            r12 = r39
            if (r11 != r12) goto L_0x05ab
            goto L_0x05a4
        L_0x05a2:
            r12 = r39
        L_0x05a4:
            r16 = r24
            goto L_0x05ad
        L_0x05a7:
            r15 = r38
            r12 = r39
        L_0x05ab:
            r16 = 0
        L_0x05ad:
            if (r10 != 0) goto L_0x05bd
            if (r16 == 0) goto L_0x05b2
            goto L_0x05bd
        L_0x05b2:
            r29 = r2
            r23 = r3
            r20 = r5
            r3 = r22
            r22 = r4
            goto L_0x0624
        L_0x05bd:
            android.view.ViewGroup r20 = r1.getContainer()
            boolean r20 = androidx.core.view.ViewCompat.isLaidOut(r20)
            if (r20 != 0) goto L_0x0606
            boolean r20 = androidx.fragment.app.FragmentManager.isLoggingEnabled(r18)
            if (r20 == 0) goto L_0x05f8
            r29 = r2
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r23 = r3
            java.lang.String r3 = "SpecialEffectsController: Container "
            java.lang.StringBuilder r2 = r2.append(r3)
            android.view.ViewGroup r3 = r1.getContainer()
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = " has not been laid out. Completing operation "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r11)
            java.lang.String r2 = r2.toString()
            r3 = r22
            android.util.Log.v(r3, r2)
            goto L_0x05fe
        L_0x05f8:
            r29 = r2
            r23 = r3
            r3 = r22
        L_0x05fe:
            r9.completeSpecialEffect()
            r22 = r4
            r20 = r5
            goto L_0x0624
        L_0x0606:
            r29 = r2
            r23 = r3
            r3 = r22
            androidx.fragment.app.SpecialEffectsController$Operation r2 = r9.getOperation()
            androidx.fragment.app.Fragment r2 = r2.getFragment()
            r22 = r4
            androidx.core.os.CancellationSignal r4 = r9.getSignal()
            r20 = r5
            androidx.fragment.app.DefaultSpecialEffectsController$9 r5 = new androidx.fragment.app.DefaultSpecialEffectsController$9
            r5.<init>(r9, r11)
            r7.setListenerForTransitionEnd(r2, r14, r4, r5)
        L_0x0624:
            r5 = r20
            r4 = r22
            r2 = r29
            r22 = r3
            r3 = r23
            goto L_0x057c
        L_0x0630:
            r15 = r38
            r12 = r39
            r29 = r2
            r23 = r3
            r20 = r5
            r3 = r22
            r22 = r4
            android.view.ViewGroup r2 = r1.getContainer()
            boolean r2 = androidx.core.view.ViewCompat.isLaidOut(r2)
            if (r2 != 0) goto L_0x0649
            return r22
        L_0x0649:
            r2 = 4
            androidx.fragment.app.FragmentTransition.setViewVisibility(r6, r2)
            r10 = r30
            java.util.ArrayList r11 = r7.prepareSetNameOverridesReordered(r10)
            boolean r2 = androidx.fragment.app.FragmentManager.isLoggingEnabled(r18)
            if (r2 == 0) goto L_0x06d1
            java.lang.String r2 = ">>>>> Beginning transition <<<<<"
            android.util.Log.v(r3, r2)
            java.lang.String r2 = ">>>>> SharedElementFirstOutViews <<<<<"
            android.util.Log.v(r3, r2)
            java.util.Iterator r2 = r25.iterator()
        L_0x0668:
            boolean r4 = r2.hasNext()
            java.lang.String r5 = " Name: "
            java.lang.String r8 = "View: "
            if (r4 == 0) goto L_0x0699
            java.lang.Object r4 = r2.next()
            android.view.View r4 = (android.view.View) r4
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.StringBuilder r8 = r9.append(r8)
            java.lang.StringBuilder r8 = r8.append(r4)
            java.lang.StringBuilder r5 = r8.append(r5)
            java.lang.String r8 = androidx.core.view.ViewCompat.getTransitionName(r4)
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r5 = r5.toString()
            android.util.Log.v(r3, r5)
            goto L_0x0668
        L_0x0699:
            java.lang.String r2 = ">>>>> SharedElementLastInViews <<<<<"
            android.util.Log.v(r3, r2)
            java.util.Iterator r2 = r10.iterator()
        L_0x06a2:
            boolean r4 = r2.hasNext()
            if (r4 == 0) goto L_0x06d1
            java.lang.Object r4 = r2.next()
            android.view.View r4 = (android.view.View) r4
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.StringBuilder r9 = r9.append(r8)
            java.lang.StringBuilder r9 = r9.append(r4)
            java.lang.StringBuilder r9 = r9.append(r5)
            java.lang.String r1 = androidx.core.view.ViewCompat.getTransitionName(r4)
            java.lang.StringBuilder r1 = r9.append(r1)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r3, r1)
            r1 = r34
            goto L_0x06a2
        L_0x06d1:
            android.view.ViewGroup r1 = r34.getContainer()
            r7.beginDelayedTransition(r1, r14)
            android.view.ViewGroup r8 = r34.getContainer()
            r9 = r25
            r12 = r26
            r7.setNameOverridesReordered(r8, r9, r10, r11, r12)
            r4 = 0
            androidx.fragment.app.FragmentTransition.setViewVisibility(r6, r4)
            r7.swapSharedElementTargets(r0, r9, r10)
            return r22
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.DefaultSpecialEffectsController.startTransitions(java.util.List, java.util.List, boolean, androidx.fragment.app.SpecialEffectsController$Operation, androidx.fragment.app.SpecialEffectsController$Operation):java.util.Map");
    }

    /* access modifiers changed from: package-private */
    public void retainMatchingViews(ArrayMap<String, View> sharedElementViews, Collection<String> transitionNames) {
        Iterator<Map.Entry<String, View>> iterator = sharedElementViews.entrySet().iterator();
        while (iterator.hasNext()) {
            if (!transitionNames.contains(ViewCompat.getTransitionName(iterator.next().getValue()))) {
                iterator.remove();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void captureTransitioningViews(ArrayList<View> transitioningViews, View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (!ViewGroupCompat.isTransitionGroup(viewGroup)) {
                int count = viewGroup.getChildCount();
                for (int i = 0; i < count; i++) {
                    View child = viewGroup.getChildAt(i);
                    if (child.getVisibility() == 0) {
                        captureTransitioningViews(transitioningViews, child);
                    }
                }
            } else if (!transitioningViews.contains(view)) {
                transitioningViews.add(viewGroup);
            }
        } else if (!transitioningViews.contains(view)) {
            transitioningViews.add(view);
        }
    }

    /* access modifiers changed from: package-private */
    public void findNamedViews(Map<String, View> namedViews, View view) {
        String transitionName = ViewCompat.getTransitionName(view);
        if (transitionName != null) {
            namedViews.put(transitionName, view);
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = viewGroup.getChildAt(i);
                if (child.getVisibility() == 0) {
                    findNamedViews(namedViews, child);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void applyContainerChanges(SpecialEffectsController.Operation operation) {
        operation.getFinalState().applyState(operation.getFragment().mView);
    }

    private static class SpecialEffectsInfo {
        private final SpecialEffectsController.Operation mOperation;
        private final CancellationSignal mSignal;

        SpecialEffectsInfo(SpecialEffectsController.Operation operation, CancellationSignal signal) {
            this.mOperation = operation;
            this.mSignal = signal;
        }

        /* access modifiers changed from: package-private */
        public SpecialEffectsController.Operation getOperation() {
            return this.mOperation;
        }

        /* access modifiers changed from: package-private */
        public CancellationSignal getSignal() {
            return this.mSignal;
        }

        /* access modifiers changed from: package-private */
        public boolean isVisibilityUnchanged() {
            SpecialEffectsController.Operation.State currentState = SpecialEffectsController.Operation.State.from(this.mOperation.getFragment().mView);
            SpecialEffectsController.Operation.State finalState = this.mOperation.getFinalState();
            return currentState == finalState || !(currentState == SpecialEffectsController.Operation.State.VISIBLE || finalState == SpecialEffectsController.Operation.State.VISIBLE);
        }

        /* access modifiers changed from: package-private */
        public void completeSpecialEffect() {
            this.mOperation.completeSpecialEffect(this.mSignal);
        }
    }

    private static class AnimationInfo extends SpecialEffectsInfo {
        private FragmentAnim.AnimationOrAnimator mAnimation;
        private boolean mIsPop;
        private boolean mLoadedAnim = false;

        AnimationInfo(SpecialEffectsController.Operation operation, CancellationSignal signal, boolean isPop) {
            super(operation, signal);
            this.mIsPop = isPop;
        }

        /* access modifiers changed from: package-private */
        public FragmentAnim.AnimationOrAnimator getAnimation(Context context) {
            if (this.mLoadedAnim) {
                return this.mAnimation;
            }
            this.mAnimation = FragmentAnim.loadAnimation(context, getOperation().getFragment(), getOperation().getFinalState() == SpecialEffectsController.Operation.State.VISIBLE, this.mIsPop);
            this.mLoadedAnim = true;
            return this.mAnimation;
        }
    }

    private static class TransitionInfo extends SpecialEffectsInfo {
        private final boolean mOverlapAllowed;
        private final Object mSharedElementTransition;
        private final Object mTransition;

        TransitionInfo(SpecialEffectsController.Operation operation, CancellationSignal signal, boolean isPop, boolean providesSharedElementTransition) {
            super(operation, signal);
            Object obj;
            Object obj2;
            boolean z;
            if (operation.getFinalState() == SpecialEffectsController.Operation.State.VISIBLE) {
                if (isPop) {
                    obj2 = operation.getFragment().getReenterTransition();
                } else {
                    obj2 = operation.getFragment().getEnterTransition();
                }
                this.mTransition = obj2;
                if (isPop) {
                    z = operation.getFragment().getAllowReturnTransitionOverlap();
                } else {
                    z = operation.getFragment().getAllowEnterTransitionOverlap();
                }
                this.mOverlapAllowed = z;
            } else {
                if (isPop) {
                    obj = operation.getFragment().getReturnTransition();
                } else {
                    obj = operation.getFragment().getExitTransition();
                }
                this.mTransition = obj;
                this.mOverlapAllowed = true;
            }
            if (!providesSharedElementTransition) {
                this.mSharedElementTransition = null;
            } else if (isPop) {
                this.mSharedElementTransition = operation.getFragment().getSharedElementReturnTransition();
            } else {
                this.mSharedElementTransition = operation.getFragment().getSharedElementEnterTransition();
            }
        }

        /* access modifiers changed from: package-private */
        public Object getTransition() {
            return this.mTransition;
        }

        /* access modifiers changed from: package-private */
        public boolean isOverlapAllowed() {
            return this.mOverlapAllowed;
        }

        public boolean hasSharedElementTransition() {
            return this.mSharedElementTransition != null;
        }

        public Object getSharedElementTransition() {
            return this.mSharedElementTransition;
        }

        /* access modifiers changed from: package-private */
        public FragmentTransitionImpl getHandlingImpl() {
            FragmentTransitionImpl transitionImpl = getHandlingImpl(this.mTransition);
            FragmentTransitionImpl sharedElementTransitionImpl = getHandlingImpl(this.mSharedElementTransition);
            if (transitionImpl == null || sharedElementTransitionImpl == null || transitionImpl == sharedElementTransitionImpl) {
                return transitionImpl != null ? transitionImpl : sharedElementTransitionImpl;
            }
            throw new IllegalArgumentException("Mixing framework transitions and AndroidX transitions is not allowed. Fragment " + getOperation().getFragment() + " returned Transition " + this.mTransition + " which uses a different Transition  type than its shared element transition " + this.mSharedElementTransition);
        }

        private FragmentTransitionImpl getHandlingImpl(Object transition) {
            if (transition == null) {
                return null;
            }
            if (FragmentTransition.PLATFORM_IMPL != null && FragmentTransition.PLATFORM_IMPL.canHandle(transition)) {
                return FragmentTransition.PLATFORM_IMPL;
            }
            if (FragmentTransition.SUPPORT_IMPL != null && FragmentTransition.SUPPORT_IMPL.canHandle(transition)) {
                return FragmentTransition.SUPPORT_IMPL;
            }
            throw new IllegalArgumentException("Transition " + transition + " for fragment " + getOperation().getFragment() + " is not a valid framework Transition or AndroidX Transition");
        }
    }
}
