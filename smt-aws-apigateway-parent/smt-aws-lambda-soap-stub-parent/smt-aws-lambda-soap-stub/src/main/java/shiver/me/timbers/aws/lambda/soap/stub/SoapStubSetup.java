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

package shiver.me.timbers.aws.lambda.soap.stub;

import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import shiver.me.timbers.aws.common.Env;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Clock;

public class SoapStubSetup {

    static Digester digester() throws TransformerConfigurationException, IOException {
        // The AWS Lambda JAR is extracted into chroot, so we must use a standard file lookup to access JAR resources.
        return new Digester(cleaner(new FileInputStream("remove-namespaces.xslt")), new MessageDigestFactory());
    }

    static Cleaner cleaner(InputStream stream) throws TransformerConfigurationException, IOException {
        return new Cleaner(
            new SoapMessages(new SoapMessageFactory()),
            new TransformerFactory(
                javax.xml.transform.TransformerFactory.newInstance().newTemplates(new StreamSource(stream))
            )
        );
    }

    static StubbingRepository repository() {
        return new StubbingRepository(new Env(), AmazonS3ClientBuilder.defaultClient(), Clock.systemDefaultZone());
    }
}