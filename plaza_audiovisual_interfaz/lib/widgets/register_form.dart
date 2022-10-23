import 'package:get/get.dart';
import 'package:plaza_audiovisual_interfaz/api/my_api.dart';
import 'package:plaza_audiovisual_interfaz/model/user.dart';
import 'package:plaza_audiovisual_interfaz/utils/dialogs.dart';
import 'package:plaza_audiovisual_interfaz/utils/responsive.dart';
import 'package:plaza_audiovisual_interfaz/widgets/input_text.dart';
import 'package:flutter/material.dart';

class RegisterForm extends StatefulWidget {
  @override
  _RegisterForm createState() => _RegisterForm();
}

class _RegisterForm extends State<RegisterForm> {

  final GlobalKey<FormState> _formKey = GlobalKey();
  User user = User();
  _submit() async {
    final isOk = _formKey.currentState?.validate();
    MyApi myApi = MyApi.instance;
    await myApi.register(user: user, context: context);
  }

  @override
  Widget build(BuildContext context) {

    final Responsive responsive = Responsive.of(context);

    return Positioned(
      top: responsive.dp(29),
      bottom: 30,
      left: 20,
      right: 20,
      child: Form(
        key: _formKey,
        child: Column(
          children: [
            Container(
              height: responsive.dp(36),
              child: ListView(
                children: [
                  InputText(
                    keyBoardType: TextInputType.name,
                    label: "USERNAME",
                    fontSize: responsive.dp(1.6),
                    onChanged: (text){
                      user.username = text.obs;
                    },
                    validator: (text) {
                      if (text!.isEmpty){
                        return "Invalid user name";
                      }
                      return null;
                    },
                  ),InputText(
                    keyBoardType: TextInputType.name,
                    label: "NAME",
                    fontSize: responsive.dp(1.6),
                    onChanged: (text){
                      user.name!.value = text;
                    },
                    validator: (text) {
                      if (text!.isEmpty){
                        return "Invalid name";
                      }
                      return null;
                    },
                  ),InputText(
                    keyBoardType: TextInputType.name,
                    label: "LASTNAME",
                    fontSize: responsive.dp(1.6),
                    onChanged: (text){
                      user.lastName = text.obs;
                    },
                    validator: (text) {
                      if (text!.isEmpty){
                        return "Invalid last name";
                      }
                      return null;
                    },
                  ),
                  SizedBox(height: responsive.dp(2)),
                  InputText(
                    keyBoardType: TextInputType.emailAddress,
                    label: "EMAIL ADRESS",
                    fontSize: responsive.dp(1.6),
                    onChanged: (text){
                      user.email = text.obs;
                    },
                    validator: (text) {
                      if (!text!.contains("@")){
                        return "Invalid email";
                      } else if(!text!.contains(".com")) {
                        return "Invalid email";
                      }
                      return null;
                    },
                  ),
                  SizedBox(height: responsive.dp(2)),
                  InputText(
                    keyBoardType: TextInputType.phone,
                    label: "PHONE NUMBER",
                    fontSize: responsive.dp(1.6),
                    onChanged: (text){
                      user.phoneNumber = text.obs;
                    },
                    validator: (text) {
                      if (text!.isEmpty){
                        return "Invalid phone number";
                      } else if(text.length < 9) {
                        return "Invalid phone number";
                      } else if(text.length > 15) {
                        return "Invalid phone number";
                      }
                      return null;
                    },
                  ),
                  SizedBox(height: responsive.dp(2)),
                  InputText(
                    keyBoardType: TextInputType.text,
                    label: "PASSWORD",
                    fontSize: responsive.dp(1.6),
                    obscureText: true,
                    onChanged: (text){
                      user.pasword = text.obs;
                    },
                    validator: (text) {
                      if (text!.isEmpty){
                        return "Invalid password";
                      }
                      return null;
                    },
                  ),
                  SizedBox(height: responsive.dp(2)),
                  InputText(
                    keyBoardType: TextInputType.text,
                    label: "PASSWORD CONFIRM",
                    fontSize: responsive.dp(1.6),
                    obscureText: true,
                    onChanged: (text){
                      user.passwordConfirm = text.obs;
                    },
                    validator: (text) {
                      if (text! != user.pasword){
                        return "Invalid password confirm";
                      }
                      return null;
                    },
                  ),

                  SizedBox(height: responsive.dp(2)),
                  InputText(
                    keyBoardType: TextInputType.text,
                    label: "DESCRIPTION",
                    fontSize: responsive.dp(1.6),
                    onChanged: (text){
                    user.description = text.obs;
                    },
                    validator: (text) {
                      if (text!.isEmpty) {
                        return "Invalid description";
                      }
                      return null;
                    }
                  ),
                  SizedBox(height: responsive.dp(5)),
                ],
              ),
            ),
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
                  "Sign Up",
                  style: TextStyle(
                      fontSize: responsive.dp(1.6)
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

}