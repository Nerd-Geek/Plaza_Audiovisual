import 'package:get/get_rx/src/rx_types/rx_types.dart';
import 'package:plaza_audiovisual_interfaz/utils/responsive.dart';
import 'package:plaza_audiovisual_interfaz/widgets/input_text.dart';
import 'package:flutter/material.dart';

import '../api/my_api.dart';
import '../model/user.dart';

class LoginForm extends StatefulWidget {
  @override
  _LoginFormState createState() => _LoginFormState();
}

class _LoginFormState extends State<LoginForm> {

  final GlobalKey<FormState> _formKey = GlobalKey();
  User user = User();

  _submit() async {
    final isOk = _formKey.currentState?.validate();
    MyApi myApi = MyApi.instance;
    await myApi.login(user: user, context: context);
  }

  @override
  Widget build(BuildContext context) {
    
    final Responsive responsive = Responsive.of(context);
    
    return Positioned(
        bottom: 30,
        left: 20,
        right: 20,
        child: Form(
          key: _formKey,
          child: Column(
            children: [
            InputText(
            keyBoardType: TextInputType.text,
              label: "USER NAME",
              fontSize: responsive.dp(1.6),
              onChanged: (text){
                user.username = text.obs;
              },
              validator: (text) {
                if (text!.isEmpty) {
                  return "Invalid user name";
                }
                return null;
              },
            ),

          SizedBox(height: responsive.dp(2)),
          Container(
            decoration: const BoxDecoration(
                border: Border(
                    bottom: BorderSide(
                        color: Colors.black12
                    )
                )
            ),
            child: Row(
              children: [
                Expanded(
                  child: InputText(
                    label: "PASSWORD",
                    obscureText: true,
                    borderEnable: false,
                    fontSize: responsive.dp(1.6),
                    onChanged: (text){
                      user.pasword = text.obs;
                      print(user.pasword);
                    },
                    validator: (text) {
                      if (text == null || text.isEmpty) {
                        return "Ivalid password";
                      }
                      return null;
                    },
                  ),
                ),
                TextButton(
                    onPressed: () {  },
                    style: const ButtonStyle(
                      foregroundColor: MaterialStatePropertyAll<Color>(Colors.black),
                    ),
                    child: Text(
                      "Forgot Password",
                      style: TextStyle(
                          fontWeight: FontWeight.bold,
                          fontSize: responsive.dp(1.6)
                      ),
                    )
                ),
              ],
            ),
          ),
          SizedBox(height: responsive.dp(5)),
          SizedBox(
            width: double.infinity,
            child: OutlinedButton (
              onPressed: _submit,
              style: ButtonStyle(
                foregroundColor: const MaterialStatePropertyAll<Color>(Colors.white),
                backgroundColor: const MaterialStatePropertyAll<Color>(Colors.pinkAccent),
                padding: MaterialStateProperty.all<EdgeInsets>(
                    const EdgeInsets.symmetric(vertical: 10)),
              ),
              child: Text(
                "Sign In",
                style: TextStyle(
                    fontSize: responsive.dp(1.6)
                ),
              ),
            ),
          ),
          SizedBox(height: responsive.dp(2)),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text(
                "New to Friendly Desi?  ",
                style: TextStyle(
                    fontSize: responsive.dp(1.6)
                ),
              ),
              TextButton(
                onPressed: () {
                  Navigator.pushNamed(context, 'register');
                },
                style: const ButtonStyle(
                  foregroundColor: MaterialStatePropertyAll<Color>(Colors.pinkAccent),
                ),
                child: Text(
                  "Sign Up",
                  style: TextStyle(
                      fontSize: responsive.dp(1.6)
                  ),
                ),
              )
            ],
          ),
          SizedBox(height: responsive.dp(12)),
          ],
        ),
      ),
    );
  }

}