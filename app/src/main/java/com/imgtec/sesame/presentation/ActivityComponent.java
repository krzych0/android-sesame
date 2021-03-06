/*
 * <b>Copyright (c) 2017, Imagination Technologies Limited and/or its affiliated group companies
 *  and/or licensors. </b>
 *
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are permitted
 *  provided that the following conditions are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice, this list of conditions
 *      and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice, this list of
 *      conditions and the following disclaimer in the documentation and/or other materials provided
 *      with the distribution.
 *
 *  3. Neither the name of the copyright holder nor the names of its contributors may be used to
 *      endorse or promote products derived from this software without specific prior written
 *      permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 *  IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 *  DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 *  WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 *  WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package com.imgtec.sesame.presentation;

import com.imgtec.di.HasComponent;
import com.imgtec.di.PerActivity;
import com.imgtec.sesame.app.ApplicationComponent;
import com.imgtec.sesame.presentation.fragments.ControllerFragment;
import com.imgtec.sesame.presentation.fragments.LogsFragment;
import com.imgtec.sesame.presentation.fragments.MainFragment;
import com.imgtec.sesame.presentation.fragments.StatisticsFragment;

import dagger.Component;

@PerActivity
@Component(
    dependencies = ApplicationComponent.class,
    modules = {
        ActivityModule.class
    }
)
public interface ActivityComponent {

  class Initializer {

    private Initializer() {}

    static ActivityComponent init(BaseActivity activity) {
      return DaggerActivityComponent
          .builder()
          .applicationComponent(((HasComponent<ApplicationComponent>) activity.getApplicationContext()).getComponent())
          .activityModule(new ActivityModule(activity))
          .build();
    }
  }

  void inject(MainActivity activity);
  void inject(MainFragment fragment);
  void inject(StatisticsFragment fragment);
  void inject(ControllerFragment fragment);
  void inject(LogsFragment fragment);

}
