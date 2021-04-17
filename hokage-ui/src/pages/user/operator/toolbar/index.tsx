import React from 'react'
import { Button, Col, Row } from 'antd'
import {
  InfoCircleOutlined,
  UserAddOutlined,
  UsergroupDeleteOutlined as UserGroupDeleteOutlined
} from '@ant-design/icons'
import AddOperator from './add-operator'
import { observer } from 'mobx-react'
import store from '../store';
import { UserAction } from '../../../../axios/action'
import { getHokageUid } from '../../../../libs'

@observer
export class Toolbar extends React.Component {

  delete = () => {
    alert("delete operators bat")
  }

  add = () => {
    store.isModalVisible = true
  }

  onModalOk = (value: any) => {
    UserAction.addSupervisor({
      operatorId: getHokageUid(),
      serverIds: [],
      userIds: value.userIds || []
    }).finally(() => store.isModalVisible = false)
  }

  onModalCancel = () => {
    store.isModalVisible = false
  }

  render() {
    return (
      <Row gutter={24} style={{ backgroundColor: '#e6f7ff', border: '#91d5ff', margin: '0 0' }}>
        <Col span={12} style={{ display: 'flex', alignItems: 'center' }}>
          <span>
            <InfoCircleOutlined translate="true" style={{ color: "#1890ff" }} />
            已选择{<span style={{ color: "blue" }}>{store.selectedRowKeys.length}</span>}项
          </span>
        </Col>
        <Col span={12} >
          <span style={{ float: 'right' }}>
            {store.selectedRowKeys.length > 0 ? (
              <span style={{ paddingRight: '64px' }}>
                <Button icon={<UserGroupDeleteOutlined translate="true" />} onClick={this.delete}>批量删除</Button>
              </span>
            ) : null}
            <Button icon={<UserAddOutlined translate="true" />} onClick={this.add}>添加</Button>
            <AddOperator onModalOk={this.onModalOk} onModalCancel={this.onModalCancel} />
          </span>
        </Col>
      </Row>
    )
  }
}