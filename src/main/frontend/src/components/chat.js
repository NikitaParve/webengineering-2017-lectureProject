/**
 * Chat-Komponente für Nachrichten
 *
 * @author Luan Hajzeraj on 06.08.2017.
 */

import React, {Component} from 'react';

import {getUserByID, sendChatMessage, getUnreadMessages} from '../util/Http';
import User from '../util/User';


//Template von: https://bootsnipp.com/snippets/ZlkBn
class Chat extends Component {
    constructor(props) {
        super(props);

        this.state = {
            id: props.match.params.id,
            messages: [],
            chatPartner: undefined,
            myMessage: '',
            interval: undefined,
            newMessage: undefined
        };

    }

    //left=messages von mir
    //right=messages des partners
    componentWillMount() {
        getUserByID(this.state.id)
            .then(response => {
                this.setState({
                    chatPartner: response.data,
                    messages: [{side: 'right', text: response.data.usertext}]
                });
            });
    }

    //Wenn Komponente schon gerendert ist: Lade alle Nachrichten und definiere einen Abfrage-intervall
    componentDidMount() {
        let intervalID = setInterval(() => {
            getUnreadMessages(this.state.id)
                .then(response => {
                    var array = this.state.messages;
                    response.data.forEach(message => {
                        array.push({side: 'right', text: message.message});
                    });

                    this.setState({messages: array});
                });
        }, 5000);

        this.setState({interval: intervalID});

    }

    //Wenn die Komponente verlassen wird: resete den interval
    componentWillUnmount() {
        clearInterval(this.state.interval);
    }

    handleChangeMessageField(event) {
        this.setState({myMessage: event.target.value})
    }

    sendMessage() {
        sendChatMessage(User.id, this.state.id, this.state.myMessage)
            .then(() => {
                let array = this.state.messages;
                array.push({side: 'left', text: this.state.myMessage});
                this.setState({messages: array, myMessage: ''});
            });
    }

    render() {
        return (
            <div>
                <div class="chat_window">
                    <div class="top_menu">

                        <div class="title">{this.state.chatPartner && this.state.chatPartner.nickname }</div>
                    </div>
                    <ul class="messages">
                        {this.state.messages.map((message, index) => {
                            return <li class={"appeared message " + message.side} key={index}>
                                <div class="avatar"></div>
                                <div class="text_wrapper">
                                    <div class="text">{message.text}</div>
                                </div>
                            </li>;
                        })}
                    </ul>
                    <div class="bottom_wrapper clearfix" onKeyPress={(e) => {
                        if (e.charCode == 13) {
                            this.sendMessage()
                        }
                    }
                    }>
                        <div class="message_input_wrapper">
                            <input class="message_input" value={this.state.myMessage}
                                   onChange={this.handleChangeMessageField.bind(this)}
                                   placeholder="Type your message here..."/>
                        </div>
                        <div class="send_message" onClick={this.sendMessage.bind(this)}>
                            <div class="icon"></div>
                            <div class="text">Send</div>
                        </div>

                    </div>
                </div>
            </div>

        );
    }
}

export default Chat;