import React from 'react'
import { message, Row, Col, Spin } from 'antd'
import ServerCard from './server-card'
import ApplyServerPrompt from './apply-server-prompt'
import { ServerVO } from '../../axios/action/server/server-type'
import { searchServer } from '../server/util'

type FileServerProps = {
  actionName: string
  action: (serverVO: ServerVO) => void
}

type FileServerState = {
  isModalVisible: boolean,
  dataSource: ServerVO[],
  loading: boolean
}

export default class ServerCardPanel extends React.Component<FileServerProps, FileServerState> {

  state = {
    isModalVisible: false,
    dataSource: [],
    loading: true
  }

  componentDidMount() {
    searchServer(this)
  }

  applyServer = () => {
    window.location.href = "/app/server/all"
  }

  onFinish = (value: any) => {
    console.log(value)
  }

  resetFields = () => {
    console.log("reset")
  }

  delete = () => {
    alert("delete operators bat")
  }

  sync = () => {
    alert("sync operator")
  }

  onModalOk = (value: any) => {
    console.log(value)
    this.setState({ ...this.state, isModalVisible: false })
    message.loading({ content: 'Loading...', key: 'addUser' });
    setTimeout(() => {
      message.success({ content: 'Loaded!', key: 'addUser', duration: 2 });
    }, 2000)
  }

  onModalCancel = () => {
    this.setState({ ...this.state, isModalVisible: false })
    message.warning({ content: '添加用户已经取消!', key: 'addUser', duration: 2 });
  }

  enterFileManagement = () => {
    alert('enter file management')
  }

  renderServerCards = (dataSource: ServerVO[]) => {
    return dataSource.map(serverVO => {
      return (
        <Col span={8}>
          <ServerCard
            serverVO={serverVO}
            actionName={this.props.actionName}
            action={this.props.action}
          />
        </Col>
      )
    })
  }

  renderServerCardPanel = () => {
    const { dataSource } = this.state
    if (!dataSource || dataSource.length === 0) {
      return <ApplyServerPrompt />
    }
    return (
      <div style={{ backgroundColor: '#FFFFFF' }}>
        <Row gutter={24} style={{ margin: '5px 0px' }}>
          {this.renderServerCards(dataSource)}
        </Row>
      </div>
    )
  }

  render() {
    const { loading } = this.state
    return (
      <Spin spinning={loading}>{ this.renderServerCardPanel() }</Spin>
    )
  }
}
