package com.example.azolotarev.test.Service;

import android.app.AlertDialog;
import android.os.Build;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;

import android.transition.Fade;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.util.Log;
import android.widget.ImageView;
import com.example.azolotarev.test.Data.Local.PersistentStorage;
import com.example.azolotarev.test.Data.Net.Connect;
import com.example.azolotarev.test.Data.Net.Net;
import com.example.azolotarev.test.Domain.Authorization.AuthorizationInteractor;
import com.example.azolotarev.test.Domain.DepartmentsList.DepartmentInteractor;
import com.example.azolotarev.test.Domain.EmployeePage.EmployeeInteractor;
import com.example.azolotarev.test.R;
import com.example.azolotarev.test.Repository.Repository;
import com.example.azolotarev.test.UI.Authorization.AuthorizationFragment;
import com.example.azolotarev.test.UI.Authorization.AuthorizationPresenter;
import com.example.azolotarev.test.UI.Main.DepartmentsListRoot.DepartmentListFragment;
import com.example.azolotarev.test.UI.Main.DepartmentsListRoot.DepartmentListPresenter;
import com.example.azolotarev.test.UI.Main.EmployeePage.LargeImage.LargeImageFragment;
import com.example.azolotarev.test.UI.Main.EmployeePage.LargeImage.LargeImagePresenter;
import com.example.azolotarev.test.UI.Main.EmployeeViewPager.EmployeePagerFragment;
import com.example.azolotarev.test.UI.Main.EmployeeViewPager.EmployeePagerPresenter;

public class Router {

    private static final long MOVE_DEFAULT_TIME = 1000;
    private static final long FADE_DEFAULT_TIME = 300;

    public static void showDepartmentsList(FragmentActivity activity) {
        DepartmentListFragment fragment=DepartmentListFragment.newInstance();
        PresenterManager.addPresenter(new DepartmentListPresenter(new DepartmentInteractor(
                        new Repository(PersistentStorage.get(),new Net(new Connect())))),
                fragment.getClass().getName());
        FragmentTransaction transaction=activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer,fragment).commit();
    }

    public static void showEmployeeDetail(FragmentActivity activity, String positionInTree, String id, String filter) {
        EmployeePagerFragment fragment=EmployeePagerFragment.newInstance(positionInTree,id,filter);
        if(PresenterManager.getPresenter(fragment.getClass().getName())==null) {
            PresenterManager.addPresenter(new EmployeePagerPresenter(new EmployeeInteractor(
                            new Repository(PersistentStorage.get(),
                                    new Net(new Connect())))),
                    fragment.getClass().getName());
        }
        FragmentTransaction transaction=activity.getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.fragmentContainer, fragment).commit();
    }

    public static void showLogOut(FragmentActivity activity) {
        AuthorizationFragment fragment=AuthorizationFragment.newInstance();
        PresenterManager.addPresenter(new AuthorizationPresenter(new AuthorizationInteractor(new Repository(PersistentStorage.get(),new Net(new Connect())))),
                fragment.getClass().getName());
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
    }

    public static void showLargeImage(FragmentActivity activity, String id, Fragment previousFragment, ImageView avatarView) {
        LargeImageFragment fragment=LargeImageFragment.newInstance(id);
        if(PresenterManager.getPresenter(fragment.getClass().getName())==null) {
            PresenterManager.addPresenter(new LargeImagePresenter(new EmployeeInteractor(
                            new Repository(PersistentStorage.get(),
                                    new Net(new Connect())))),
                    fragment.getClass().getName());
        }
/*        Fade exitFade=new Fade();
        exitFade.setDuration(FADE_DEFAULT_TIME);
        previousFragment.setExitTransition(exitFade);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TransitionSet enterTransitionSet = new TransitionSet();
            enterTransitionSet.addTransition(TransitionInflater.from(ContextManager.getContext()).inflateTransition(android.R.transition.move));
            enterTransitionSet.setDuration(MOVE_DEFAULT_TIME);
            enterTransitionSet.setStartDelay(FADE_DEFAULT_TIME);
            fragment.setSharedElementEnterTransition(enterTransitionSet);

            Fade enterFade = new Fade();
            enterFade.setStartDelay(MOVE_DEFAULT_TIME + FADE_DEFAULT_TIME);
            enterFade.setDuration(FADE_DEFAULT_TIME);
            fragment.setSharedElementEnterTransition(enterFade);
*//*
            Log.i("TAG", "shared "+ViewCompat.getTransitionName(avatarView));
            Transition changeTransform=TransitionInflater.from(ContextManager.getContext()).inflateTransition(R.transition.change_image_transform);
            Transition explodeTransform=TransitionInflater.from(ContextManager.getContext()).inflateTransition(android.R.transition.explode);

            previousFragment.setSharedElementReturnTransition(changeTransform);
            previousFragment.setExitTransition(explodeTransform);

            fragment.setSharedElementEnterTransition(changeTransform);
            fragment.setExitTransition(explodeTransform);
        *//*
        }*/
        FragmentTransaction transaction=activity.getSupportFragmentManager().beginTransaction();
        transaction.addSharedElement(avatarView, ViewCompat.getTransitionName(avatarView));
        transaction.addToBackStack(null);
        transaction.add(R.id.fragmentContainer, fragment).commitAllowingStateLoss();
    }
}
