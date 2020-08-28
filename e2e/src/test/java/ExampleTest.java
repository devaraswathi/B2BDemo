import org.jsmart.zerocode.core.domain.Scenario;
import org.jsmart.zerocode.core.domain.TargetEnv;
import org.jsmart.zerocode.core.runner.ZeroCodeUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@TargetEnv("e2e-environment.properties")
@RunWith(ZeroCodeUnitRunner.class)
public class ExampleTest {

  @Test
  @Scenario("exampleTestCase-WHEN-actuator-health-check-endpoint-is-called-EXPECT-success.yaml")
  public void checkActuatorHealthExampleTest() {

  }
}
