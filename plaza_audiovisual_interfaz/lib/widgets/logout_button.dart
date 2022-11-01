import 'package:flutter/material.dart';

import '../utils/auth.dart';
import '../utils/responsive.dart';

class LogoutButton extends StatelessWidget {
  const LogoutButton({super.key});


  @override
  Widget build(BuildContext context) {
    final Responsive responsive = Responsive.of(context);
    return Container(
          margin: EdgeInsets.only(
            left: responsive.wp(85),
            top:  responsive.wp(10)
          ),
          child: TextButton(

            onPressed: () => Auth.instance.logOut(context),
            style: const ButtonStyle(
            foregroundColor: MaterialStatePropertyAll<Color>(Colors.green),
          ),
          child: Icon(
          Icons.power_settings_new_outlined,
          size: responsive.wp(8),
          )
      ),
    );
  }

}