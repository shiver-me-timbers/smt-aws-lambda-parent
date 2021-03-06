/*
 * Copyright 2017 Karl Bennett
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package shiver.me.timbers.aws.lambda.cr.ssm;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.model.GetParametersRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParametersResult;
import org.apache.log4j.Logger;

import java.util.List;

import static java.lang.String.format;

/**
 * @author Karl Bennett
 */
class ParametersService {

    private final Logger log = Logger.getLogger(getClass());

    private final AWSSimpleSystemsManagement simpleSystemsManagement;

    ParametersService(AWSSimpleSystemsManagement simpleSystemsManagement) {
        this.simpleSystemsManagement = simpleSystemsManagement;
    }

    Parameters getParameters(GetParametersResourceRequest request) {
        log.info("Requesting the SSM parameters.");
        final GetParametersResult result = simpleSystemsManagement
            .getParameters(new GetParametersRequest().withNames(request.getParameterNames()));
        final List<String> invalidParameters = result.getInvalidParameters();
        if (!invalidParameters.isEmpty()) {
            log.error(format("Invalid parameters supplied: %s", result.getInvalidParameters()));
            throw new InvalidParametersException(invalidParameters);
        }
        return new Parameters(result.getParameters());
    }
}
