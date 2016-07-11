package org.aksw.limes.core.io.config;

import org.aksw.limes.core.evaluation.evaluator.EvaluatorType;
import org.aksw.limes.core.ml.algorithm.LearningParameter;
import org.aksw.limes.core.ml.algorithm.MLImplementationType;

import java.util.*;

/**
 * @author Mohamed Sherif {@literal <}sherif {@literal @} informatik.uni-leipzig.de{@literal >}
 * @version Jun 3, 2016
 */
public class Configuration implements IConfiguration {

    private static final String DEFAULT = "default";
    
    protected KBInfo sourceInfo = new KBInfo();
    protected KBInfo targetInfo = new KBInfo();

    protected String metricExpression = new String();

    protected String acceptanceRelation = new String();

    protected String verificationRelation = new String();

    protected double acceptanceThreshold;
    protected String acceptanceFile;

    protected double verificationThreshold;
    protected String verificationFile;

    protected Map<String, String> prefixes = new HashMap<String, String>();

    protected String outputFormat;

    protected String executionRewriter = DEFAULT;
    protected String executionPlanner = DEFAULT;
    protected String executionEngine = DEFAULT;

    protected int granularity = 2;

    protected String mlAlgorithmName = new String();
    protected List<LearningParameter> mlAlgorithmParameters = new ArrayList<>();
    protected MLImplementationType mlImplementationType = MLImplementationType.UNSUPERVISED;
    private String mlTrainingDataFile = null;
    private EvaluatorType mlPseudoFMeasure = null;

    public Configuration() {
    }

    public Configuration(KBInfo sourceInfo, KBInfo targetInfo, String metricExpression, String acceptanceRelation,
            String verificationRelation, double acceptanceThreshold, String acceptanceFile,
            double verificationThreshold, String verificationFile, Map<String, String> prefixes, String outputFormat,
            String executionRewriter, String executionPlanner, String executionEngine, int granularity,
            String mlAlgorithmName, List<LearningParameter> mlParameters, MLImplementationType mlImplementationType,
            String mlTrainingDataFile, EvaluatorType mlPseudoFMeasure) {
        super();
        this.sourceInfo = sourceInfo;
        this.targetInfo = targetInfo;
        this.metricExpression = metricExpression;
        this.acceptanceRelation = acceptanceRelation;
        this.verificationRelation = verificationRelation;
        this.acceptanceThreshold = acceptanceThreshold;
        this.acceptanceFile = acceptanceFile;
        this.verificationThreshold = verificationThreshold;
        this.verificationFile = verificationFile;
        this.prefixes = prefixes;
        this.outputFormat = outputFormat;
        this.executionRewriter = executionRewriter;
        this.executionPlanner = executionPlanner;
        this.executionEngine = executionEngine;
        this.granularity = granularity;
        this.mlAlgorithmName = mlAlgorithmName;
        this.mlAlgorithmParameters = mlParameters;
        this.mlImplementationType = mlImplementationType;
        this.mlTrainingDataFile = mlTrainingDataFile;
        this.mlPseudoFMeasure = mlPseudoFMeasure;
    }

    public void addMlAlgorithmParameter(String mlParameterName, String mlParameterValue) {
        LearningParameter lp = new LearningParameter();
        lp.setName(mlParameterName);
        lp.setValue(mlParameterValue);
        this.mlAlgorithmParameters.add(lp);
    }

    public void addPrefixes(String label, String namespace) {
        this.prefixes.put(label, namespace);
    }

    public String getAcceptanceFile() {
        return acceptanceFile;
    }

    public String getAcceptanceRelation() {
        return acceptanceRelation;
    }

    public double getAcceptanceThreshold() {
        return acceptanceThreshold;
    }

    @Override
    public Set<String> getConfigurationParametersNames() {
        return new HashSet<String>(Arrays.asList("sourceInfo", "targetInfo", "metricExpression", "acceptanceRelation",
                "verificationRelation", "acceptanceThreshold", "acceptanceFile", "verificationThreshold",
                "verificationFile", "exemplars", "prefixes", "outputFormat", "executionPlan", "granularity",
                "recallRegulator", "recallThreshold"));
    }

    public int getGranularity() {
        return granularity;
    }

    public String getMetricExpression() {
        return metricExpression;
    }

    public String getMlAlgorithmName() {
        return mlAlgorithmName;
    }

    public MLImplementationType getMlImplementationType() {
        return mlImplementationType;
    }

    public List<LearningParameter> getMlAlgorithmParameters() {
        return mlAlgorithmParameters;
    }

    public EvaluatorType getMlPseudoFMeasure() {
        return mlPseudoFMeasure;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public Map<String, String> getPrefixes() {
        return prefixes;
    }

    public KBInfo getSourceInfo() {
        return sourceInfo;
    }

    public KBInfo getTargetInfo() {
        return targetInfo;
    }

    public String getTrainingDataFile() {
        return mlTrainingDataFile;
    }

    public String getVerificationFile() {
        return verificationFile;
    }

    public String getVerificationRelation() {
        return verificationRelation;
    }

    public double getVerificationThreshold() {
        return verificationThreshold;
    }

    public void setAcceptanceFile(String acceptanceFile) {
        this.acceptanceFile = acceptanceFile;
    }

    public void setAcceptanceRelation(String acceptanceRelation) {
        this.acceptanceRelation = acceptanceRelation;
    }

    public void setAcceptanceThreshold(double acceptanceThreshold) {
        this.acceptanceThreshold = acceptanceThreshold;
    }

    public void setGranularity(int granularity) {
        this.granularity = granularity;
    }

    public void setMetricExpression(String metricExpression) {
        this.metricExpression = metricExpression;
    }

    public void setMlAlgorithmName(String mlAlgorithmName) {
        this.mlAlgorithmName = mlAlgorithmName;
    }

    public void setMlImplementationType(MLImplementationType mlImplementationType) {
        this.mlImplementationType = mlImplementationType;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    public void setPrefixes(HashMap<String, String> prefixes) {
        this.prefixes = prefixes;
    }

    public void setSourceInfo(KBInfo sourceInfo) {
        this.sourceInfo = sourceInfo;
    }

    public void setTargetInfo(KBInfo targetInfo) {
        this.targetInfo = targetInfo;
    }

    public void setTrainingDataFile(String trainingDataFile) {
        this.mlTrainingDataFile = trainingDataFile;
    }

    public void setVerificationFile(String verificationFile) {
        this.verificationFile = verificationFile;
    }

    public void setVerificationRelation(String verificationRelation) {
        this.verificationRelation = verificationRelation;
    }

    public void setVerificationThreshold(double verificationThreshold) {
        this.verificationThreshold = verificationThreshold;
    }

    public String getExecutionRewriter() {
        return executionRewriter;
    }

    public void setExecutionRewriter(String executionRewriter) {
        this.executionRewriter = executionRewriter;
    }

    public String getExecutionPlanner() {
        return executionPlanner;
    }

    public void setExecutionPlanner(String executionPlanner) {
        this.executionPlanner = executionPlanner;
    }

    public String getExecutionEngine() {
        return executionEngine;
    }

    public void setExecutionEngine(String executionEngine) {
        this.executionEngine = executionEngine;
    }

    public String getMlTrainingDataFile() {
        return mlTrainingDataFile;
    }

    @Override
    public String toString() {
        return "Configuration [sourceInfo=" + sourceInfo + ", targetInfo=" + targetInfo + ", metricExpression="
                + metricExpression + ", acceptanceRelation=" + acceptanceRelation + ", verificationRelation="
                + verificationRelation + ", acceptanceThreshold=" + acceptanceThreshold + ", acceptanceFile="
                + acceptanceFile + ", verificationThreshold=" + verificationThreshold + ", verificationFile="
                + verificationFile + ", prefixes=" + prefixes + ", outputFormat=" + outputFormat
                + ", executionRewriter=" + executionRewriter + ", executionPlanner=" + executionPlanner
                + ", executionEngine=" + executionEngine + ", granularity=" + granularity + ", mlAlgorithmName="
                + mlAlgorithmName + ", mlParameters=" + mlAlgorithmParameters + ", mlImplementationType=" + mlImplementationType
                + ", mlTrainingDataFile=" + mlTrainingDataFile + ", mlPseudoFMeasure=" + mlPseudoFMeasure + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((acceptanceFile == null) ? 0 : acceptanceFile.hashCode());
        result = prime * result + ((acceptanceRelation == null) ? 0 : acceptanceRelation.hashCode());
        long temp;
        temp = Double.doubleToLongBits(acceptanceThreshold);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((executionEngine == null) ? 0 : executionEngine.hashCode());
        result = prime * result + ((executionPlanner == null) ? 0 : executionPlanner.hashCode());
        result = prime * result + ((executionRewriter == null) ? 0 : executionRewriter.hashCode());
        result = prime * result + granularity;
        result = prime * result + ((metricExpression == null) ? 0 : metricExpression.hashCode());
        result = prime * result + ((mlAlgorithmName == null) ? 0 : mlAlgorithmName.hashCode());
        result = prime * result + ((mlImplementationType == null) ? 0 : mlImplementationType.hashCode());
        result = prime * result + ((mlAlgorithmParameters == null) ? 0 : mlAlgorithmParameters.hashCode());
        result = prime * result + ((mlPseudoFMeasure == null) ? 0 : mlPseudoFMeasure.hashCode());
        result = prime * result + ((mlTrainingDataFile == null) ? 0 : mlTrainingDataFile.hashCode());
        result = prime * result + ((outputFormat == null) ? 0 : outputFormat.hashCode());
        result = prime * result + ((prefixes == null) ? 0 : prefixes.hashCode());
        result = prime * result + ((sourceInfo == null) ? 0 : sourceInfo.hashCode());
        result = prime * result + ((targetInfo == null) ? 0 : targetInfo.hashCode());
        result = prime * result + ((verificationFile == null) ? 0 : verificationFile.hashCode());
        result = prime * result + ((verificationRelation == null) ? 0 : verificationRelation.hashCode());
        temp = Double.doubleToLongBits(verificationThreshold);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Configuration other = (Configuration) obj;
        if (acceptanceFile == null) {
            if (other.acceptanceFile != null)
                return false;
        } else if (!acceptanceFile.equals(other.acceptanceFile))
            return false;
        if (acceptanceRelation == null) {
            if (other.acceptanceRelation != null)
                return false;
        } else if (!acceptanceRelation.equals(other.acceptanceRelation))
            return false;
        if (Double.doubleToLongBits(acceptanceThreshold) != Double.doubleToLongBits(other.acceptanceThreshold))
            return false;
        if (executionEngine == null) {
            if (other.executionEngine != null)
                return false;
        } else if (!executionEngine.equals(other.executionEngine))
            return false;
        if (executionPlanner == null) {
            if (other.executionPlanner != null)
                return false;
        } else if (!executionPlanner.equals(other.executionPlanner))
            return false;
        if (executionRewriter == null) {
            if (other.executionRewriter != null)
                return false;
        } else if (!executionRewriter.equals(other.executionRewriter))
            return false;
        if (granularity != other.granularity)
            return false;
        if (metricExpression == null) {
            if (other.metricExpression != null)
                return false;
        } else if (!metricExpression.equals(other.metricExpression))
            return false;
        if (mlAlgorithmName == null) {
            if (other.mlAlgorithmName != null)
                return false;
        } else if (!mlAlgorithmName.equals(other.mlAlgorithmName))
            return false;
        if (mlImplementationType != other.mlImplementationType)
            return false;
        if (mlAlgorithmParameters == null) {
            if (other.mlAlgorithmParameters != null)
                return false;
        } else if (!mlAlgorithmParameters.equals(other.mlAlgorithmParameters))
            return false;
        if (mlPseudoFMeasure != other.mlPseudoFMeasure)
            return false;
        if (mlTrainingDataFile == null) {
            if (other.mlTrainingDataFile != null)
                return false;
        } else if (!mlTrainingDataFile.equals(other.mlTrainingDataFile))
            return false;
        if (outputFormat == null) {
            if (other.outputFormat != null)
                return false;
        } else if (!outputFormat.equals(other.outputFormat))
            return false;
        if (prefixes == null) {
            if (other.prefixes != null)
                return false;
        } else if (!prefixes.equals(other.prefixes))
            return false;
        if (sourceInfo == null) {
            if (other.sourceInfo != null)
                return false;
        } else if (!sourceInfo.equals(other.sourceInfo))
            return false;
        if (targetInfo == null) {
            if (other.targetInfo != null)
                return false;
        } else if (!targetInfo.equals(other.targetInfo))
            return false;
        if (verificationFile == null) {
            if (other.verificationFile != null)
                return false;
        } else if (!verificationFile.equals(other.verificationFile))
            return false;
        if (verificationRelation == null) {
            if (other.verificationRelation != null)
                return false;
        } else if (!verificationRelation.equals(other.verificationRelation))
            return false;
        if (Double.doubleToLongBits(verificationThreshold) != Double.doubleToLongBits(other.verificationThreshold))
            return false;
        return true;
    }

    public void setMlTrainingDataFile(String mlTrainingDataFile) {
        this.mlTrainingDataFile = mlTrainingDataFile;
    }

    public void setPrefixes(Map<String, String> prefixes) {
        this.prefixes = prefixes;
    }

    public void setMlAlgorithmParameters(List<LearningParameter> mlParameters) {
        this.mlAlgorithmParameters = mlParameters;
    }

    public void setMlPseudoFMeasure(EvaluatorType mlPseudoFMeasure) {
        this.mlPseudoFMeasure = mlPseudoFMeasure;
    }
}
