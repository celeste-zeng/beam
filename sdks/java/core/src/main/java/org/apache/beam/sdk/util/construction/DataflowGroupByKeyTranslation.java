/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.beam.sdk.util.construction;

import com.google.auto.service.AutoService;
import java.util.Collections;
import java.util.Map;
import org.apache.beam.model.pipeline.v1.RunnerApi;
import org.apache.beam.model.pipeline.v1.RunnerApi.FunctionSpec;
import org.apache.beam.sdk.runners.AppliedPTransform;
import org.apache.beam.sdk.transforms.DataflowGroupByKey;
import org.apache.beam.sdk.transforms.PTransform;

/**
 * Utility methods for translating a {@link DataflowGroupByKey} to and from {@link RunnerApi}
 * representations.
 */
public class DataflowGroupByKeyTranslation {

  static class DataflowGroupByKeyTranslator
      implements PTransformTranslation.TransformPayloadTranslator<DataflowGroupByKey<?, ?>> {
    @Override
    public String getUrn() {
      return PTransformTranslation.GROUP_BY_KEY_TRANSFORM_URN;
    }

    @Override
    @SuppressWarnings("nullness")
    public FunctionSpec translate(
        AppliedPTransform<?, ?, DataflowGroupByKey<?, ?>> transform, SdkComponents components) {
      return FunctionSpec.newBuilder().setUrn(getUrn(transform.getTransform())).build();
    }
  }

  /** Registers {@link DataflowGroupByKeyTranslator}. */
  @AutoService(TransformPayloadTranslatorRegistrar.class)
  public static class Registrar implements TransformPayloadTranslatorRegistrar {
    @Override
    @SuppressWarnings("rawtypes")
    public Map<
            ? extends Class<? extends PTransform>,
            ? extends PTransformTranslation.TransformPayloadTranslator>
        getTransformPayloadTranslators() {
      return Collections.singletonMap(DataflowGroupByKey.class, new DataflowGroupByKeyTranslator());
    }
  }
}
