import 'package:flutter/material.dart';

class InputText extends StatelessWidget {

  final String label;
  final TextInputType keyBoardType;
  final bool obscureText, borderEnable;
  final double fontSize;
  final void Function(String text) onChanged;
  final String? Function(String?) validator;
  InputText({
    this.label = "",
    this.keyBoardType = TextInputType.text,
    this.obscureText = false,
    this.borderEnable = true,
    this.fontSize = 15,
    required this.onChanged,
    required this.validator,
  });

  @override
  Widget build(BuildContext context) {
    return TextFormField(
      keyboardType: keyBoardType,
      obscureText: obscureText,
      validator: validator,
      onChanged: onChanged,
      style: TextStyle(
        fontSize: fontSize,
      ),
      decoration: InputDecoration(
          labelText: label,
          contentPadding: const EdgeInsets.symmetric(vertical: 5),
          enabledBorder: borderEnable? const UnderlineInputBorder(
            borderSide: BorderSide(
              color: Colors.black12
            )
          ): InputBorder.none,
          labelStyle: const TextStyle(
              color: Colors.black45,
              fontWeight: FontWeight.w500
          )
      ),
    );
  }
  

}