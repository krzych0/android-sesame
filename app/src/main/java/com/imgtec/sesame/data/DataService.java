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

package com.imgtec.sesame.data;

import com.imgtec.sesame.data.api.pojo.Api;
import com.imgtec.sesame.data.api.pojo.DoorsAction;
import com.imgtec.sesame.data.api.pojo.DoorsEntrypoint;
import com.imgtec.sesame.data.api.pojo.DoorsState;
import com.imgtec.sesame.data.api.pojo.DoorsStatistics;
import com.imgtec.sesame.data.api.pojo.Logs;

import java.util.concurrent.atomic.AtomicReference;

/**
 *
 */
public interface DataService {

  void requestApi(DataCallback<DataService, Api> callback);

  void requestLogs(DataCallback<DataService, Logs> callback);

  void requestStatistics(DataCallback<DataService, DoorsStatistics> callback);

  void requestState(DataCallback<DataService, DoorsState> callback);

  void clearCache();

  AtomicReference<DoorsEntrypoint> getCachedEntryPoint();

  void startPollingDoorState(DataCallback<DataService, DoorsState> callback);
  void stopPollingDoorState();

  DoorsState getLastDoorsState();

  void performOperate();

  void openDoors(DataCallback<DataService, DoorsAction> callback);

  void closeDoors(DataCallback<DataService, DoorsAction> callback);
}
