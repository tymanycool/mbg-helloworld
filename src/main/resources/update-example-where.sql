<sql id="UpdateExampleWhereClause">
  <isNotNull property="example.oredCriteria">
    WHERE
    <iterate  property="example.oredCriteria" conjunction="OR" removeFirstPrepend="iterate">
      <isEqual property="example.oredCriteria[].valid"  prepend="AND" compareValue="true" removeFirstPrepend="true">(
        <iterate  property="example.oredCriteria[].criteria" >
          <isEqual property="example.oredCriteria[].criteria[].noValue" compareValue="true" removeFirstPrepend="true" prepend="AND">
            $example.oredCriteria[].criteria[].condition$
          </isEqual>
          <isEqual property="example.oredCriteria[].criteria[].singleValue" compareValue="true" removeFirstPrepend="true" prepend="AND">
            $example.oredCriteria[].criteria[].condition$ #example.oredCriteria[].criteria[].value#
          </isEqual>
          <isEqual property="example.oredCriteria[].criteria[].betweenValue" compareValue="true" removeFirstPrepend="true" prepend="AND">
            $example.oredCriteria[].criteria[].condition$ #example.oredCriteria[].criteria[].value# AND #example.oredCriteria[].criteria[].secondValue#
          </isEqual>
          <isEqual property="example.oredCriteria[].criteria[].listValue" compareValue="true" removeFirstPrepend="true" prepend="AND">
            $example.oredCriteria[].criteria[].condition$
            <iterate  property="example.oredCriteria[].criteria[].value" conjunction="," >
              (#example.oredCriteria[].criteria[].value[]#)
            </iterate >
          </isEqual>
        </iterate >
      )</isEqual>
    </iterate >
  </isNotNull>
</sql>