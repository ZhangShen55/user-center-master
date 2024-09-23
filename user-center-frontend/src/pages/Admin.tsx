import {PageContainer} from '@ant-design/pro-components';
import '@umijs/max';
import React from 'react';

const Admin: React.FC = (props) => {
  const {children} = props;
  return (
      <PageContainer>
        {children}
      </PageContainer>
    );
};
export default Admin;
